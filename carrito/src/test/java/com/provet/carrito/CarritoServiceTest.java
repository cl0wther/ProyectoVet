package com.provet.carrito;

import com.provet.carrito.client.CatalogoClient;
import com.provet.carrito.dto.ItemCarritoRequestDTO;
import com.provet.carrito.dto.MedicamentoDTO;
import com.provet.carrito.model.Carrito;
import com.provet.carrito.model.ItemCarrito;
import com.provet.carrito.repository.CarritoRepository;
import com.provet.carrito.repository.ItemCarritoRepository;
import com.provet.carrito.service.CarritoService;
import feign.FeignException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias de CarritoService.
 */
@ExtendWith(MockitoExtension.class)
public class CarritoServiceTest {

    @Mock
    private CarritoRepository carritoRepository;

    @Mock
    private ItemCarritoRepository itemCarritoRepository;

    @Mock
    private CatalogoClient catalogoClient;

    @InjectMocks
    private CarritoService carritoService;

    @Test
    @DisplayName("quitarItem: elimina el ítem del carrito exitosamente")
    void quitarItem_eliminaItem() {
        Carrito carrito = new Carrito("maria");
        carrito.setId(1L);

        MedicamentoDTO medicamento = TestDataFactory.unMedicamentoDTO();
        ItemCarrito item = new ItemCarrito(medicamento.getId(), medicamento.getNombre(),
                medicamento.getPrecio(), 2, carrito);
        item.setId(5L);
        carrito.getItems().add(item);

        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carrito));
        when(itemCarritoRepository.findById(5L)).thenReturn(Optional.of(item));
        when(carritoRepository.save(any(Carrito.class))).thenReturn(carrito);

        carritoService.quitarItem(1L, 5L);

        // El carrito debe quedar sin ítems
        assertThat(carrito.getItems()).isEmpty();
        verify(carritoRepository).save(carrito);
    }

    @Test
    @DisplayName("vaciar: elimina absolutamente todos los medicamentos del carrito")
    void vaciar_eliminaTodosLosItems() {
        // Arrancamos con un carrito que tiene 2 ítems distintos
        Carrito carrito = new Carrito("ana");
        carrito.setId(1L);
        
        MedicamentoDTO medicamento = TestDataFactory.unMedicamentoDTO();
        carrito.getItems().add(new ItemCarrito(medicamento.getId(), medicamento.getNombre(),
                medicamento.getPrecio(), 1, carrito));
                
        carrito.getItems().add(new ItemCarrito(medicamento.getId() + 1, "Antiparasitario Canino",
                new BigDecimal("9900.00"), 2, carrito));

        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carrito));
        when(carritoRepository.save(any(Carrito.class))).thenReturn(carrito);

        carritoService.vaciar(1L);

        assertThat(carrito.getItems()).isEmpty();
        verify(carritoRepository).save(carrito);
    }

    @Test
    @DisplayName("agregarItem: lanza excepción si el carrito no existe en BD")
    void agregarItem_carritoNoExiste() {
        ItemCarritoRequestDTO request = TestDataFactory.unItemRequest(1L);
        
        when(carritoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> carritoService.agregarItem(1L, request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Carrito no encontrado");
    }

    @Test
    @DisplayName("agregarItem: lanza excepción si el medicamento no está en el catálogo")
    void agregarItem_medicamentoNoExiste() {
        Carrito carrito = new Carrito("luis");
        carrito.setId(1L);
        ItemCarritoRequestDTO request = TestDataFactory.unItemRequest(1L);

        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carrito));
        // Simulamos un 404 Not Found del microservicio de catálogo
        when(catalogoClient.obtenerMedicamento(1L)).thenThrow(FeignException.NotFound.class);

        assertThatThrownBy(() -> carritoService.agregarItem(1L, request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("no existe en el catálogo");
    }

    @Test
    @DisplayName("agregarItem: suma la cantidad si el medicamento ya estaba previamente en el carrito")
    void agregarItem_sumaCantidadExistente() {
        Carrito carrito = new Carrito("pedro");
        carrito.setId(1L);
        
        MedicamentoDTO medicamento = TestDataFactory.unMedicamentoDTO();
        medicamento.setId(10L);

        // Agregamos un ítem inicial con cantidad 2
        ItemCarrito itemExistente = new ItemCarrito(medicamento.getId(), medicamento.getNombre(), 
                                                    medicamento.getPrecio(), 2, carrito);
        carrito.getItems().add(itemExistente);

        // Cliente solicita agregar 3 unidades adicionales del mismo fármaco
        ItemCarritoRequestDTO request = TestDataFactory.unItemRequest(10L);
        request.setCantidad(3);

        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carrito));
        when(catalogoClient.obtenerMedicamento(10L)).thenReturn(medicamento);
        when(carritoRepository.save(any(Carrito.class))).thenReturn(carrito);

        carritoService.agregarItem(1L, request);

        // La cantidad total debe ser 5
        assertThat(itemExistente.getCantidad()).isEqualTo(5); 
        verify(carritoRepository).save(carrito);
    }

    @Test
    @DisplayName("agregarItem: crea un nuevo registro de ítem si el medicamento no estaba en el carrito")
    void agregarItem_creaNuevoItem() {
        Carrito carrito = new Carrito("sofia");
        carrito.setId(1L);
        
        MedicamentoDTO medicamento = TestDataFactory.unMedicamentoDTO();
        medicamento.setId(20L);

        ItemCarritoRequestDTO request = TestDataFactory.unItemRequest(20L);
        request.setCantidad(1);

        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carrito));
        when(catalogoClient.obtenerMedicamento(20L)).thenReturn(medicamento);
        when(carritoRepository.save(any(Carrito.class))).thenAnswer(i -> i.getArgument(0));

        carritoService.agregarItem(1L, request);

        assertThat(carrito.getItems()).hasSize(1);
        assertThat(carrito.getItems().get(0).getMedicamentoId()).isEqualTo(20L);
        verify(carritoRepository).save(carrito);
    }
}
