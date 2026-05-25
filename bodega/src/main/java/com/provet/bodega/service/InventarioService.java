package com.provet.bodega.service;

import com.provet.bodega.client.CatalogoClient;
import com.provet.bodega.dto.*;
import com.provet.bodega.model.Inventario;
import com.provet.bodega.repository.InventarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventarioService {

    private final InventarioRepository inventarioRepository;
    private final CatalogoClient catalogoClient;

    @Transactional
    public InventarioResponseDTO registrarProducto(InventarioRequestDTO request) {
        log.info("Registrando nueva ubicación en bodega para el producto ID: {}", request.getIdProducto());

        MedicamentoDTO medicamento = catalogoClient.obtenerMedicamentoPorId(request.getIdProducto());
        log.info("Producto validado en catálogo: {} (SKU: {})", medicamento.getNombre(), medicamento.getSku());

        if (inventarioRepository.findByIdProducto(request.getIdProducto()).isPresent()) {
            throw new RuntimeException("El producto ya tiene un registro de inventario. Usa la actualización de stock.");
        }

        Inventario inventario = new Inventario();
        inventario.setIdProducto(request.getIdProducto());
        inventario.setStockActual(request.getStockInicial());
        inventario.setStockMinimo(request.getStockMinimo());
        inventario.setUbicacion(request.getUbicacion());

        if (request.getStockInicial() > 0) {
            inventario.setUltimaReposicion(LocalDateTime.now());
        }

        Inventario guardado = inventarioRepository.save(inventario);
        return mapearAResponse(guardado);
    }
    
    

    @Transactional
    public void procesarVenta(AjusteStockDTO ajuste) {
        


        log.info("Procesando salida de bodega: {} unidades del producto ID {}", ajuste.getCantidad(), ajuste.getIdProducto());

        Inventario inventario = inventarioRepository.findByIdProducto(ajuste.getIdProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado en la bodega física."));

        if (inventario.getStockActual() < ajuste.getCantidad()) {
            log.error("Quiebre de stock. Se intentó vender {} pero solo hay {}", ajuste.getCantidad(), inventario.getStockActual());
            throw new RuntimeException("Stock insuficiente para el producto ID: " + ajuste.getIdProducto());
        }

        inventario.setStockActual(inventario.getStockActual() - ajuste.getCantidad());
        inventarioRepository.save(inventario);
        
        log.info("Stock actualizado. Quedan {} unidades en {}", inventario.getStockActual(), inventario.getUbicacion());
    }

    @Transactional(readOnly = true)
    public InventarioResponseDTO consultarStockPorProducto(Long idProducto) {
        Inventario inventario = inventarioRepository.findByIdProducto(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto sin registro de inventario."));
        return mapearAResponse(inventario);
    }

    @Transactional(readOnly = true)
    public List<InventarioResponseDTO> obtenerProductosEnStockCritico() {
        log.info("Generando reporte de productos en stock crítico...");
        List<Inventario> criticos = inventarioRepository.buscarProductosEnStockCritico();
        return criticos.stream().map(this::mapearAResponse).collect(Collectors.toList());
    }
    public Boolean verificarStock(Long idProducto, Integer cantidadPedida) {
        return inventarioRepository.findByIdProducto(idProducto)
            .map(inventario -> inventario.getStockActual() >= cantidadPedida)
            .orElse(false);
    }

// 2. Método "Adaptador" para llamar a tu lógica existente
    @Transactional
    public void descontarStock(Long idProducto, Integer cantidadPedida) {
        AjusteStockDTO ajuste = new AjusteStockDTO(idProducto, cantidadPedida);
        this.procesarVenta(ajuste);
    }

    private InventarioResponseDTO mapearAResponse(Inventario inv) {
        // Aquí hacemos el cálculo inteligente para facilitarle la vida al Frontend
        boolean enAlerta = inv.getStockActual() <= inv.getStockMinimo();
        
        return new InventarioResponseDTO(
                inv.getId(),
                inv.getIdProducto(),
                inv.getStockActual(),
                inv.getStockMinimo(),
                inv.getUbicacion(),
                inv.getUltimaReposicion(),
                enAlerta
        );
    }
}