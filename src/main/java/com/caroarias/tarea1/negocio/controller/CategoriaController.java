package com.caroarias.tarea1.negocio.controller;

import com.caroarias.tarea1.negocio.models.dtos.CategoriaDTO;
import com.caroarias.tarea1.negocio.service.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> obtenerTodosCategorias() {
        return categoriaService.obtenerTodosCategorias();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> obtenerCategoriaPorId(@PathVariable Long id) {
        return categoriaService.obtenerCategoriaPorId(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER-ADMIN-ROLE')")
    public ResponseEntity<CategoriaDTO> crearCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        return categoriaService.crearCategoria(categoriaDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER-ADMIN-ROLE')")
    public ResponseEntity<CategoriaDTO> actualizarCategoria(@PathVariable Long id,
                                               @RequestBody CategoriaDTO categoriaDTO) {
        return categoriaService.actualizarCategoria(id, categoriaDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER-ADMIN-ROLE')")
    public ResponseEntity<Void> eliminarCategoriapPorId(@PathVariable Long id) {
        return categoriaService.eliminarCategoriapPorId(id);
    }
}