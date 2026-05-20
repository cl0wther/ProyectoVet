package com.provet.carrito.service;

import com.provet.carrito.client.CatalogoClient;
import com.provet.carrito.dto.*;
import com.provet.carrito.model.Carrito;
import com.provet.carrito.model.ItemCarrito;
import com.provet.carrito.repository.CarritoRepository;
import com.provet.carrito.repository.ItemCarritoRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final ItemCarritoRepository itemCarritoRepository;
    private final CatalogoClient catalogoClient;

    @Transactional
    public CarritoResponseDTO crear(String usuario) {
        Carrito nuevoCarrito = new Carrito(usuario);
        return mapToDTO(carritoRepository.save(nuevoCarrito));
    }

    @Transactional(readOnly = true)
    public Optional<CarritoResponseDTO> obtenerPorId(Long id) {
        return carritoRepository.findById(id).map(this::mapToDTO);
    }

    @Transactional(readOnly = true)
    public List<CarritoResponseDTO> obtenerPorUsuario(String usuario) {
        return carritoRepository.findByUsuario(usuario).stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Transactional
    public CarritoResponseDTO agregarItem(Long carritoId, ItemCarritoRequestDTO dto) {
        // 1. Validar que el carrito exista en la veterinaria
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado con ID: " + carritoId));

        // 2. Consultar síncronamente al catálogo por HTTP via Feign para verificar el fármaco
        MedicamentoDTO medicamento = consultarMedicamento(dto.getMedicamentoId());

        // 3. Verificar si el medicamento ya estaba en el carro de la mascota
        Optional<ItemCarrito> existente = carrito.getItems().stream()
                .filter(item -> item.getMedicamentoId().equals(dto.getMedicamentoId()))
                .findFirst();

        if (existente.isPresent()) {
            // Si ya existe, se incrementa la cantidad solicitada
            existente.get().setCantidad(existente.get().getCantidad() + dto.getCantidad());
        } else {
            // Si es nuevo, se crea el registro con el snapshot del momento
            ItemCarrito nuevoItem = new ItemCarrito(
                    medicamento.getId(),
                    medicamento.getNombre(),
                    medicamento.getPrecio(),
                    dto.getCantidad(),
                    carrito
            );
            carrito.getItems().add(nuevoItem);
        }

        return mapToDTO(carritoRepository.save(carrito));
    }

    @Transactional
    public CarritoResponseDTO quitarItem(Long carritoId, Long itemId) {
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado con ID: " + carritoId));
        
        ItemCarrito item = itemCarritoRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Ítem de medicamento no encontrado con ID: " + itemId));
        
        if (!item.getCarrito().getId().equals(carritoId)) {
            throw new RuntimeException("El ítem " + itemId + " no pertenece al carrito " + carritoId);
        }
        
        carrito.getItems().remove(item);
        return mapToDTO(carritoRepository.save(carrito));
    }

    @Transactional
    public CarritoResponseDTO vaciar(Long carritoId) {
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado con ID: " + carritoId));
        
        carrito.getItems().clear();
        return mapToDTO(carritoRepository.save(carrito));
    }

    @Transactional
    public void eliminar(Long carritoId) {
        carritoRepository.deleteById(carritoId);
    }

    private MedicamentoDTO consultarMedicamento(Long medicamentoId) {
        try {
            return catalogoClient.obtenerMedicamento(medicamentoId);
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("El medicamento con ID " + medicamentoId + " no existe en el catálogo de Provet.");
        } catch (FeignException e) {
            log.error("Error de comunicación con provet-catalogo: {}", e.getMessage());
            throw new RuntimeException("No se pudo verificar el medicamento. Intente más tarde.");
        }
    }

    private CarritoResponseDTO mapToDTO(Carrito c) {
        CarritoResponseDTO dto = new CarritoResponseDTO();
        dto.setId(c.getId());
        dto.setUsuario(c.getUsuario());
        dto.setFechaCreacion(c.getFechaCreacion());

        List<ItemCarritoResponseDTO> itemDTOs = c.getItems().stream().map(item -> {
            ItemCarritoResponseDTO iDto = new ItemCarritoResponseDTO();
            iDto.setId(item.getId());
            iDto.setMedicamentoId(item.getMedicamentoId());
            iDto.setNombreMedicamento(item.getNombreMedicamento());
            iDto.setCantidad(item.getCantidad());
            iDto.setPrecioUnitario(item.getPrecioUnitario());
            iDto.setSubtotal(item.getPrecioUnitario().multiply(BigDecimal.valueOf(item.getCantidad())));
            return iDto;
        }).toList();

        dto.setItems(itemDTOs);
        dto.setTotal(itemDTOs.stream()
                .map(ItemCarritoResponseDTO::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        return dto;
    }
}