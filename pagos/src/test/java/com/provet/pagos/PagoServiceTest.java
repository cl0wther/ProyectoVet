package com.provet.pagos;

import com.provet.pagos.client.PedidoClient;
import com.provet.pagos.dto.PagoRequestDTO;
import com.provet.pagos.dto.PagoResponseDTO;
import com.provet.pagos.dto.PedidoDTO;
import com.provet.pagos.model.Pago;
import com.provet.pagos.repository.PagoRepository;
import com.provet.pagos.service.PagoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("PagoService - Pruebas Unitarias")
class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private PedidoClient pedidoClient;

    @InjectMocks
    private PagoService pagoService;

    @Test
    @DisplayName("procesarPago: guarda y retorna el pago cuando los montos coinciden")
    void procesarPago_montosCoinciden_procesaExitosamente() {
        PagoRequestDTO request = TestDataFactory.unPagoRequest();
        
        PedidoDTO pedidoMock = TestDataFactory.unPedidoDTO();
        pedidoMock.setTotal(request.getMontoTotal()); 

        when(pedidoClient.obtenerPedidoPorId(request.getIdPedido())).thenReturn(pedidoMock);
        
        when(pagoRepository.save(any(Pago.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PagoResponseDTO resultado = pagoService.procesarPago(request);

        assertThat(resultado.getIdPedido()).isEqualTo(request.getIdPedido());
        assertThat(resultado.getMontoTotal()).isEqualByComparingTo(request.getMontoTotal());
        assertThat(resultado.getMetodoPago()).isEqualTo(request.getMetodoPago().toUpperCase());
        assertThat(resultado.getCodigoTransaccion()).isNotNull();
        
        assertThat(resultado.getEstado()).isIn("COMPLETADO", "RECHAZADO");

        verify(pedidoClient).obtenerPedidoPorId(request.getIdPedido());
        verify(pagoRepository).save(any(Pago.class));
    }

    @Test
    @DisplayName("procesarPago: lanza excepción cuando el cliente intenta pagar un monto diferente")
    void procesarPago_montosDiferentes_lanzaExcepcion() {
        PagoRequestDTO request = TestDataFactory.unPagoRequest();
        request.setMontoTotal(new BigDecimal("100.00"));
        
        PedidoDTO pedidoMock = TestDataFactory.unPedidoDTO();
        pedidoMock.setTotal(new BigDecimal("500.00"));

        when(pedidoClient.obtenerPedidoPorId(request.getIdPedido())).thenReturn(pedidoMock);

        assertThatThrownBy(() -> pagoService.procesarPago(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("El monto ingresado no coincide con el total del pedido");

        verify(pedidoClient).obtenerPedidoPorId(request.getIdPedido());
        verify(pagoRepository, never()).save(any());
    }

    @Test
    @DisplayName("obtenerPagoPorId: retorna el pago cuando el ID existe")
    void obtenerPagoPorId_pagoExiste_retornaDTO() {
        Pago pagoMock = TestDataFactory.unPago();
        when(pagoRepository.findById(pagoMock.getId())).thenReturn(Optional.of(pagoMock));

        PagoResponseDTO resultado = pagoService.obtenerPagoPorId(pagoMock.getId());

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(pagoMock.getId());
        assertThat(resultado.getEstado()).isEqualTo(pagoMock.getEstado());
        assertThat(resultado.getCodigoTransaccion()).isEqualTo(pagoMock.getCodigoTransaccion());
        
        verify(pagoRepository).findById(pagoMock.getId());
    }

    @Test
    @DisplayName("obtenerPagoPorId: lanza excepción cuando el ID no existe")
    void obtenerPagoPorId_pagoNoExiste_lanzaExcepcion() {
        Long idInexistente = 999L;
        when(pagoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> pagoService.obtenerPagoPorId(idInexistente))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No se encontró ningún pago con el ID: " + idInexistente);
                
        verify(pagoRepository).findById(idInexistente);
    }
}