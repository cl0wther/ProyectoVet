package com.provet.pedidos;

import com.provet.pedidos.client.CatalogoClient;
import com.provet.pedidos.client.UsuariosClient;
import com.provet.pedidos.dto.MedicamentoDTO;
import com.provet.pedidos.dto.PedidoRequestDTO;
import com.provet.pedidos.dto.PedidoResponseDTO;
import com.provet.pedidos.dto.UsuarioDTO;
import com.provet.pedidos.model.Pedido;
import com.provet.pedidos.repository.PedidoRepository;
import com.provet.pedidos.service.PedidoService;
import feign.FeignException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias de PedidoService para Provet.
 *
 * PedidoService depende de dos FeignClients (CatalogoClient y UsuariosClient). 
 * Ambas llamadas HTTP se simulan con @Mock, sin levantar ningún servidor real.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("PedidoService - Pruebas Unitarias")
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private CatalogoClient catalogoClient;

    @Mock
    private UsuariosClient usuariosClient;

    @InjectMocks
    private PedidoService pedidoService;

    @Test
    @DisplayName("crearPedido: guarda el pedido cuando usuario y medicamento existen y hay stock")
    void crearPedido_usuarioYMedicamentoValidos_creaYRetornaPedido() {
        MedicamentoDTO medicamento = TestDataFactory.unMedicamentoDTO();
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setUsername("juan");

        PedidoRequestDTO request = TestDataFactory.unPedidoRequest(medicamento.getId());
        request.setUsuario("juan");
        request.setCantidad(2);

        Pedido pedidoGuardado = TestDataFactory.unPedido(medicamento, "juan", 2);

        when(usuariosClient.obtenerUsuarioPorNombre("juan")).thenReturn(usuario);
        when(catalogoClient.obtenerMedicamento(medicamento.getId())).thenReturn(medicamento);
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoGuardado);

        PedidoResponseDTO resultado = pedidoService.crearPedido(request);

        assertThat(resultado.getNombreMedicamento()).isEqualTo(medicamento.getNombre());
        assertThat(resultado.getUsuario()).isEqualTo("juan");
        verify(pedidoRepository).save(any(Pedido.class));
    }

    @Test
    @DisplayName("crearPedido: lanza excepción cuando el usuario no existe")
    void crearPedido_usuarioNoExiste_lanzaExcepcion() {
        PedidoRequestDTO request = TestDataFactory.unPedidoRequest(1L);
        request.setUsuario("usuarioInexistente");

        when(usuariosClient.obtenerUsuarioPorNombre("usuarioInexistente"))
                .thenThrow(FeignException.NotFound.class);

        assertThatThrownBy(() -> pedidoService.crearPedido(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("no se encuentra registrado");

        verify(catalogoClient, never()).obtenerMedicamento(anyLong());
        verify(pedidoRepository, never()).save(any());
    }

    @Test
    @DisplayName("crearPedido: lanza excepción cuando el medicamento no existe en catálogo")
    void crearPedido_medicamentoNoExiste_lanzaExcepcion() {
        PedidoRequestDTO request = TestDataFactory.unPedidoRequest(99L);
        request.setUsuario("juan");

        when(usuariosClient.obtenerUsuarioPorNombre("juan")).thenReturn(new UsuarioDTO());
        when(catalogoClient.obtenerMedicamento(99L)).thenThrow(FeignException.NotFound.class);

        assertThatThrownBy(() -> pedidoService.crearPedido(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("no existe");

        verify(pedidoRepository, never()).save(any());
    }

    @Test
    @DisplayName("obtenerTodos: retorna la lista completa de pedidos")
    void obtenerTodos_retornaLista() {
        MedicamentoDTO medicamento = TestDataFactory.unMedicamentoDTO();
        Pedido p1 = TestDataFactory.unPedido(medicamento, "juan", 2);
        Pedido p2 = TestDataFactory.unPedido(medicamento, "maria", 1);
        when(pedidoRepository.findAll()).thenReturn(List.of(p1, p2));

        List<PedidoResponseDTO> resultado = pedidoService.obtenerTodos();

        assertThat(resultado).hasSize(2);
        assertThat(resultado).allMatch(p -> p.getTotal().compareTo(BigDecimal.ZERO) > 0);
        verify(pedidoRepository).findAll();
    }

    @Test
    @DisplayName("obtenerPorId: retorna el pedido cuando el id existe")
    void obtenerPorId_existente_retornaPedido() {
        MedicamentoDTO medicamento = TestDataFactory.unMedicamentoDTO();
        Pedido pedido = TestDataFactory.unPedido(medicamento, "juan", 2);
        when(pedidoRepository.findById(pedido.getId())).thenReturn(Optional.of(pedido));

        Optional<PedidoResponseDTO> resultado = pedidoService.obtenerPorId(pedido.getId());

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNombreMedicamento()).isEqualTo(medicamento.getNombre());
        assertThat(resultado.get().getUsuario()).isEqualTo("juan");
    }

    @Test
    @DisplayName("obtenerPorUsuario: retorna solo los pedidos del usuario indicado")
    void obtenerPorUsuario_retornaSoloPedidosDelUsuario() {
        MedicamentoDTO medicamento = TestDataFactory.unMedicamentoDTO();
        Pedido p1 = TestDataFactory.unPedido(medicamento, "juan", 1);
        Pedido p2 = TestDataFactory.unPedido(medicamento, "juan", 3);
        when(pedidoRepository.findByUsuario("juan")).thenReturn(List.of(p1, p2));

        List<PedidoResponseDTO> resultado = pedidoService.obtenerPorUsuario("juan");

        assertThat(resultado).hasSize(2);
        assertThat(resultado).extracting(PedidoResponseDTO::getUsuario)
                .allMatch(u -> u.equals("juan"));
        verify(pedidoRepository).findByUsuario("juan");
    }

    @Test
    @DisplayName("eliminarPedido: elimina el pedido cuando el id existe")
    void eliminarPedido_pedidoExistente_llamaDelete() {
        when(pedidoRepository.existsById(1L)).thenReturn(true);

        pedidoService.eliminarPedido(1L);

        verify(pedidoRepository).deleteById(1L);
    }

    @Test
    @DisplayName("eliminarPedido: lanza excepción cuando el id no existe")
    void eliminarPedido_pedidoNoExiste_lanzaExcepcion() {
        when(pedidoRepository.existsById(999L)).thenReturn(false);

        assertThatThrownBy(() -> pedidoService.eliminarPedido(999L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No existe un pedido");

        verify(pedidoRepository, never()).deleteById(anyLong());
    }
}