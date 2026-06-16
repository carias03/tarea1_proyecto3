package com.caroarias.tarea1.negocio.service;

import com.caroarias.tarea1.negocio.mappers.CategoriaMapper;
import com.caroarias.tarea1.negocio.models.dtos.CategoriaDTO;
import com.caroarias.tarea1.negocio.models.entities.Categoria;
import com.caroarias.tarea1.negocio.repository.CategoriaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    public CategoriaService(CategoriaRepository categoriaRepository, CategoriaMapper categoriaMapper) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
    }

    public ResponseEntity<List<CategoriaDTO>> obtenerTodosCategorias() {
        List<CategoriaDTO> categorias = categoriaRepository.findAll()
                .stream()
                .map(categoriaMapper::toDto)
                .toList();
        return ResponseEntity.ok(categorias);
    }

    public ResponseEntity<CategoriaDTO> obtenerCategoriaPorId(Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        return categoria
                .map(c -> ResponseEntity.ok(categoriaMapper.toDto(c)))
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<CategoriaDTO> crearCategoria(CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaMapper.toEntity(categoriaDTO);
        Categoria saved = categoriaRepository.save(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaMapper.toDto(saved));
    }

    public ResponseEntity<CategoriaDTO> actualizarCategoria(Long id, CategoriaDTO categoriaDTO) {
        Optional<Categoria> existing = categoriaRepository.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Categoria categoria = existing.get();
        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setDescripcion(categoriaDTO.getDescripcion());

        Categoria updated = categoriaRepository.save(categoria);
        return ResponseEntity.ok(categoriaMapper.toDto(updated));
    }

    public ResponseEntity<Void> eliminarCategoriapPorId(Long id) {
        Optional<Categoria> existing = categoriaRepository.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        categoriaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}