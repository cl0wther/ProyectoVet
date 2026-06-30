package com.provet.bodega;

import com.provet.bodega.client.CatalogoClient;
import com.provet.bodega.dto.AjusteStockDTO;
import com.provet.bodega.dto.InventarioRequestDTO;
import com.provet.bodega.dto.InventarioResponseDTO;
import com.provet.bodega.dto.MedicamentoDTO;
import com.provet.bodega.model.Inventario;
import com.provet.bodega.repository.InventarioRepository;
import com.provet.bodega.service.InventarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias de InventarioService.
 * Aquí probamos la lógica de negocio real: validaciones de stock, 
 * llamadas al cliente Feign (CatalogoClient) y cálculos de estado.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("InventarioService - Pruebas Unitarias")
class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private CatalogoClient catalogoClient; 

    @InjectMocks
    private InventarioService inventarioService;

    @Test
    @DisplayName("registrarProducto: guarda y retorna DTO cuando el producto existe en catálogo y no en bodega")
    void registrarProducto_exito() {
        InventarioRequestDTO request = TestDataFactory.unInventarioRequest();

        MedicamentoDTO medicamentoMock = new MedicamentoDTO();
        medicamentoMock.setNombre("Paracetamol");
        medicamentoMock.setSku("MED-123");
        
        when(catalogoClient.obtenerMedicamentoPorId(request.getIdProducto())).thenReturn(medicamentoMock);

        when(inventarioRepository.findByIdProducto(request.getIdProducto())).thenReturn(Optional.empty());
        

        Inventario invGuardado = new Inventario();
        invGuardado.setId(1L);
        invGuardado.setIdProducto(request.getIdProducto());
        invGuardado.setStockActual(request.getStockInicial());
        invGuardado.setStockMinimo(request.getStockMinimo());
        invGuardado.setUbicacion(request.getUbicacion());
        
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(invGuardado);

        InventarioResponseDTO resultado = inventarioService.registrarProducto(request);


        assertThat(resultado.getIdProducto()).isEqualTo(request.getIdProducto());
        assertThat(resultado.getStockActual()).isEqualTo(request.getStockInicial());
        
        verify(catalogoClient).obtenerMedicamentoPorId(request.getIdProducto());
        verify(inventarioRepository).findByIdProducto(request.getIdProducto());
        verify(inventarioRepository).save(any(Inventario.class));
    }

    @Test
    @DisplayName("registrarProducto: lanza excepción si el producto ya tiene inventario")
    void registrarProducto_inventarioExistente_lanzaExcepcion() {
        InventarioRequestDTO request = TestDataFactory.unInventarioRequest();
        
        when(catalogoClient.obtenerMedicamentoPorId(request.getIdProducto())).thenReturn(new MedicamentoDTO());
        

        Inventario invExistente = TestDataFactory.unInventario();
        when(inventarioRepository.findByIdProducto(request.getIdProducto())).thenReturn(Optional.of(invExistente));


        assertThatThrownBy(() -> inventarioService.registrarProducto(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("El producto ya tiene un registro de inventario");


        verify(inventarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("procesarVenta: descuenta el stock correctamente cuando hay suficientes unidades")
    void procesarVenta_stockSuficiente_descuentaStock() {
        AjusteStockDTO ajuste = new AjusteStockDTO(10L, 5);
        
        Inventario inventario = TestDataFactory.unInventario();
        inventario.setStockActual(20); 
        
        when(inventarioRepository.findByIdProducto(ajuste.getIdProducto())).thenReturn(Optional.of(inventario));


        inventarioService.procesarVenta(ajuste);

        assertThat(inventario.getStockActual()).isEqualTo(15);
        verify(inventarioRepository).save(inventario);
    }

    @Test
    @DisplayName("procesarVenta: lanza excepción de quiebre de stock si la cantidad es mayor al stock actual")
    void procesarVenta_quiebreDeStock_lanzaExcepcion() {
        AjusteStockDTO ajuste = new AjusteStockDTO(10L, 50);
        
        Inventario inventario = TestDataFactory.unInventario();
        inventario.setStockActual(10); 
        
        when(inventarioRepository.findByIdProducto(ajuste.getIdProducto())).thenReturn(Optional.of(inventario));

        // Ejecución y validación
        assertThatThrownBy(() -> inventarioService.procesarVenta(ajuste))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Stock insuficiente");

        verify(inventarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("obtenerProductosEnStockCritico: calcula correctamente el flag enAlerta")
    void obtenerProductosEnStockCritico_mapeaEnAlertaCorrectamente() {
        Inventario invCritico = TestDataFactory.unInventario();
        invCritico.setStockActual(5);
        invCritico.setStockMinimo(10); 
        
        when(inventarioRepository.buscarProductosEnStockCritico()).thenReturn(List.of(invCritico));

        List<InventarioResponseDTO> resultado = inventarioService.obtenerProductosEnStockCritico();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getAlertaStockCritico()).isTrue();
    }

    @Test
    @DisplayName("verificarStock: retorna true si el stock es mayor o igual a lo pedido")
    void verificarStock_suficiente_retornaTrue() {
        Inventario inv = TestDataFactory.unInventario();
        inv.setStockActual(15);
        
        when(inventarioRepository.findByIdProducto(1L)).thenReturn(Optional.of(inv));

        Boolean hayStock = inventarioService.verificarStock(1L, 10);

        assertThat(hayStock).isTrue();
    }

    @Test
    @DisplayName("verificarStock: retorna false si el producto no existe o el stock es menor")
    void verificarStock_insuficiente_retornaFalse() {
        Inventario inv = TestDataFactory.unInventario();
        inv.setStockActual(5);
        
        when(inventarioRepository.findByIdProducto(1L)).thenReturn(Optional.of(inv));

        Boolean hayStock = inventarioService.verificarStock(1L, 10); // Pide 10, hay 5

        assertThat(hayStock).isFalse();
    }
}