package com.provet.pedidos.service;

import com.provet.pedidos.client.CatalogoClient;
import com.provet.pedidos.client.UsuariosClient;
import com.provet.pedidos.dto.*;
import com.provet.pedidos.model.Pedido;
import com.provet.pedidos.repository.PedidoRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final CatalogoClient catalogoClient;
    private final UsuariosClient usuariosClient;

    @Transactional
    public PedidoResponseDTO crearPedido(PedidoRequestDTO dto) {
        // 1. Validar Usuario en provet-usuarios
        try {
            usuariosClient.obtenerUsuarioPorNombre(dto.getUsuario());
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("El usuario '" + dto.getUsuario() + "' no se encuentra registrado.");
        } catch (FeignException e) {
            throw new RuntimeException("Error en la comunicación con el servicio de usuarios.");
        }

        // 2. Validar y obtener el medicamento desde provet-catalogo
        MedicamentoDTO medicamento;
        try {
            medicamento = catalogoClient.obtenerMedicamento(dto.getMedicamentoId());
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("El medicamento con ID " + dto.getMedicamentoId() + " no existe.");
        } catch (FeignException e) {
            throw new RuntimeException("Error en la comunicación con el catálogo de medicamentos.");
        }

        // 3. Validar disponibilidad de Stock
        if (medicamento.getStock() != null && medicamento.getStock() < dto.getCantidad()) {
            throw new RuntimeException("Stock insuficiente para '" + medicamento.getNombre() + "'. Disponibles: " + medicamento.getStock());
        }

        // 4. Mapear y procesar la persistencia del pedido
        Pedido pedido = new Pedido();
        pedido.setUsuario(dto.getUsuario());
        pedido.setMedicamentoId(dto.getMedicamentoId());
        pedido.setNombreMedicamento(medicamento.getNombre());
        pedido.setCantidad(dto.getCantidad());
        pedido.setPrecioUnitario(medicamento.getPrecio());
        pedido.setTotal(medicamento.getPrecio().multiply(BigDecimal.valueOf(dto.getCantidad())));
        pedido.setFechaPedido(LocalDateTime.now());

        Pedido guardado = pedidoRepository.save(pedido);
        log.info("Pedido registrado con éxito. ID asignado: {}", guardado.getId());
        return mapToDTO(guardado);
    }

    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> obtenerTodos() {
        return pedidoRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> obtenerPorUsuario(String usuario) {
        return pedidoRepository.findByUsuario(usuario).stream().map(this::mapToDTO).toList();
    }

    @Transactional(readOnly = true)
    public Optional<PedidoResponseDTO> obtenerPorId(Long id) {
        return pedidoRepository.findById(id).map(this::mapToDTO);
    }

    @Transactional
    public void eliminarPedido(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new RuntimeException("No existe un pedido registrado con el ID: " + id);
        }
        pedidoRepository.deleteById(id);
    }

    private PedidoResponseDTO mapToDTO(Pedido p) {
        PedidoResponseDTO dto = new PedidoResponseDTO();
        dto.setId(p.getId());
        dto.setUsuario(p.getUsuario());
        dto.setMedicamentoId(p.getMedicamentoId());
        dto.setNombreMedicamento(p.getNombreMedicamento());
        dto.setCantidad(p.getCantidad());
        dto.setPrecioUnitario(p.getPrecioUnitario());
        dto.setTotal(p.getTotal());
        dto.setFechaPedido(p.getFechaPedido());
        return dto;
    }
}