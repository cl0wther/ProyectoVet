package com.provet.pedidos.service;

import com.provet.pedidos.client.BodegaClient;
import com.provet.pedidos.client.CatalogoClient;
import com.provet.pedidos.client.PagosClient;
import com.provet.pedidos.client.UsuariosClient;
import com.provet.pedidos.dto.*;
import com.provet.pedidos.model.Pedido;
import com.provet.pedidos.repository.PedidoRepository;
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
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final CatalogoClient catalogoClient;
    private final UsuariosClient usuariosClient;
    private final BodegaClient bodegaClient; 
    private final PagosClient pagosClient; 

        @Transactional
        public PedidoResponseDTO crearPedido(PedidoRequestDTO request) {
        
        MedicamentoDTO med = catalogoClient.obtenerMedicamento(request.getMedicamentoId());
        BigDecimal total = med.getPrecio().multiply(BigDecimal.valueOf(request.getCantidad()));

        Boolean hayStock = bodegaClient.verificarStock(med.getId(), request.getCantidad());
        if (!hayStock) {
            throw new RuntimeException("No hay stock suficiente en la bodega para este producto.");
        }

        Pedido nuevoPedido = new Pedido();
        Pedido pedidoGuardado = pedidoRepository.save(nuevoPedido);

        PagoRequestDTO pagoRequest = new PagoRequestDTO(
            pedidoGuardado.getId(), total, request.getMetodoPago() 
        );
        pagosClient.procesarPago(pagoRequest);

        bodegaClient.descontarStock(med.getId(), request.getCantidad());

        return mapToDTO(pedidoGuardado);
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