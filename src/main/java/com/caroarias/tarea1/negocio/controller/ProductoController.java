package com.caroarias.tarea1.negocio.controller;

import com.caroarias.tarea1.negocio.models.dtos.ProductoDTO;
import com.caroarias.tarea1.negocio.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<?> obtenerTodosProductos() {
        return productoService.obtenerTodosProductos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerProductoPorId(@PathVariable Long id) {
        return productoService.obtenerProductoPorId(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER-ADMIN-ROLE')")
    public ResponseEntity<?> crearProducto(@RequestBody ProductoDTO productoDTO) {
        return productoService.crearProducto(productoDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER-ADMIN-ROLE')")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id,
                                                @RequestBody ProductoDTO productoDTO) {
        return productoService.actualizarProducto(id, productoDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER-ADMIN-ROLE')")
    public ResponseEntity<?> eliminarProductoPorId(@PathVariable Long id) {
        return productoService.eliminarProductoPorId(id);
    }

    @PatchMapping("/{productoId}/categoria/{categoriaId}")
    @PreAuthorize("hasRole('SUPER-ADMIN-ROLE')")
    public ResponseEntity<?> asignarCategoria(@PathVariable Long productoId,
                                              @PathVariable Long categoriaId) {
        return productoService.asignarCategoria(productoId, categoriaId);
    }
}
