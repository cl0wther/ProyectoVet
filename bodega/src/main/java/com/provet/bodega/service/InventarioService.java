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

    // 1. Ingresar un producto nuevo a la bodega física
    @Transactional
    public InventarioResponseDTO registrarProducto(InventarioRequestDTO request) {
        log.info("Registrando nueva ubicación en bodega para el producto ID: {}", request.getIdProducto());

        // Validamos que el producto realmente exista en la vitrina (Catálogo)
        MedicamentoDTO medicamento = catalogoClient.obtenerMedicamentoPorId(request.getIdProducto());
        log.info("Producto validado en catálogo: {} (SKU: {})", medicamento.getNombre(), medicamento.getSku());

        // Verificamos que no esté registrado ya en la bodega
        if (inventarioRepository.findByIdProducto(request.getIdProducto()).isPresent()) {
            throw new RuntimeException("El producto ya tiene un registro de inventario. Usa la actualización de stock.");
        }

        Inventario inventario = new Inventario();
        inventario.setIdProducto(request.getIdProducto());
        inventario.setStockActual(request.getStockInicial());
        inventario.setStockMinimo(request.getStockMinimo());
        inventario.setUbicacion(request.getUbicacion());
        
        // Si entra con stock, marcamos la fecha de reposición
        if (request.getStockInicial() > 0) {
            inventario.setUltimaReposicion(LocalDateTime.now());
        }

        Inventario guardado = inventarioRepository.save(inventario);
        return mapearAResponse(guardado);
    }

    // 2. EL MÉTODO ESTRELLA: Descontar stock cuando se concreta una venta
    @Transactional
    public void procesarVenta(AjusteStockDTO ajuste) {
        log.info("Procesando salida de bodega: {} unidades del producto ID {}", ajuste.getCantidad(), ajuste.getIdProducto());

        Inventario inventario = inventarioRepository.findByIdProducto(ajuste.getIdProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado en la bodega física."));

        // Regla logística estricta: No podemos vender lo que no tenemos
        if (inventario.getStockActual() < ajuste.getCantidad()) {
            log.error("Quiebre de stock. Se intentó vender {} pero solo hay {}", ajuste.getCantidad(), inventario.getStockActual());
            throw new RuntimeException("Stock insuficiente para el producto ID: " + ajuste.getIdProducto());
        }

        // Restamos las cajas que se van
        inventario.setStockActual(inventario.getStockActual() - ajuste.getCantidad());
        inventarioRepository.save(inventario);
        
        log.info("Stock actualizado. Quedan {} unidades en {}", inventario.getStockActual(), inventario.getUbicacion());
    }

    // 3. Consultar la situación de un producto específico
    @Transactional(readOnly = true)
    public InventarioResponseDTO consultarStockPorProducto(Long idProducto) {
        Inventario inventario = inventarioRepository.findByIdProducto(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto sin registro de inventario."));
        return mapearAResponse(inventario);
    }

    // 4. Reporte para el administrador: ¿Qué se nos está acabando?
    @Transactional(readOnly = true)
    public List<InventarioResponseDTO> obtenerProductosEnStockCritico() {
        log.info("Generando reporte de productos en stock crítico...");
        List<Inventario> criticos = inventarioRepository.buscarProductosEnStockCritico();
        return criticos.stream().map(this::mapearAResponse).collect(Collectors.toList());
    }

    // --- MÉTODO DE APOYO ---
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