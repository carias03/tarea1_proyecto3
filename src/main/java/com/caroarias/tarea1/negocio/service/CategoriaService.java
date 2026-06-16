package com.caroarias.tarea1.negocio.service;

import com.caroarias.tarea1.common.ApiResponse;
import com.caroarias.tarea1.negocio.mappers.CategoriaMapper;
import com.caroarias.tarea1.negocio.models.dtos.CategoriaDTO;
import com.caroarias.tarea1.negocio.models.entities.Categoria;
import com.caroarias.tarea1.negocio.repository.CategoriaRepository;
import com.caroarias.tarea1.negocio.repository.ProductoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;
    private final CategoriaMapper categoriaMapper;

    public CategoriaService(CategoriaRepository categoriaRepository, ProductoRepository productoRepository, CategoriaMapper categoriaMapper) {
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
        this.categoriaMapper = categoriaMapper;
    }

    public ResponseEntity<ApiResponse<List<CategoriaDTO>>> obtenerTodosCategorias() {
        List<CategoriaDTO> categorias = categoriaRepository.findAll()
                .stream()
                .map(categoriaMapper::toDto)
                .toList();
        return ResponseEntity.ok(new ApiResponse<>("Categorías obtenidas exitosamente", categorias));
    }

    public ResponseEntity<?> obtenerCategoriaPorId(Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        if (categoria.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("La categoría con id " + id + " no existe"));
        }
        return ResponseEntity.ok(
                new ApiResponse<>("Categoría encontrada", categoriaMapper.toDto(categoria.get()))
        );
    }

    public ResponseEntity<ApiResponse<CategoriaDTO>> crearCategoria(CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaMapper.toEntity(categoriaDTO);
        Categoria saved = categoriaRepository.save(categoria);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Categoría creada exitosamente", categoriaMapper.toDto(saved)));
    }

    public ResponseEntity<?> actualizarCategoria(Long id, CategoriaDTO categoriaDTO) {
        Optional<Categoria> existing = categoriaRepository.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("La categoría con id " + id + " no existe"));
        }

        Categoria categoria = existing.get();
        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setDescripcion(categoriaDTO.getDescripcion());

        Categoria updated = categoriaRepository.save(categoria);
        return ResponseEntity.ok(
                new ApiResponse<>("Categoría actualizada exitosamente", categoriaMapper.toDto(updated))
        );
    }

    public ResponseEntity<ApiResponse<?>> eliminarCategoriaPorId(Long id) {
        Optional<Categoria> existing = categoriaRepository.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("La categoría con id " + id + " no existe"));
        }

        if (productoRepository.existsByCategoriaId(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse<>("No se puede eliminar la categoría porque tiene productos asignados"));
        }

        categoriaRepository.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>("Categoría eliminada exitosamente"));
    }
}
