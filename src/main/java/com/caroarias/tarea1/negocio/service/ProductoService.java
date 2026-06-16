package com.caroarias.tarea1.negocio.service;

import com.caroarias.tarea1.common.ApiResponse;
import com.caroarias.tarea1.negocio.mappers.ProductoMapper;
import com.caroarias.tarea1.negocio.models.dtos.ProductoDTO;
import com.caroarias.tarea1.negocio.models.entities.Categoria;
import com.caroarias.tarea1.negocio.models.entities.Producto;
import com.caroarias.tarea1.negocio.repository.CategoriaRepository;
import com.caroarias.tarea1.negocio.repository.ProductoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProductoMapper productoMapper;

    public ProductoService(ProductoRepository productoRepository,
                           CategoriaRepository categoriaRepository,
                           ProductoMapper productoMapper) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.productoMapper = productoMapper;
    }

    public ResponseEntity<ApiResponse<List<ProductoDTO>>> obtenerTodosProductos() {
        List<ProductoDTO> productos = productoRepository.findAll()
                .stream()
                .map(productoMapper::toDto)
                .toList();
        return ResponseEntity.ok(new ApiResponse<>("Productos obtenidos exitosamente", productos));
    }

    public ResponseEntity<?> obtenerProductoPorId(Long id) {
        Optional<Producto> producto = productoRepository.findById(id);
        if (producto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("El producto con id " + id + " no existe"));
        }
        return ResponseEntity.ok(
                new ApiResponse<>("Producto encontrado", productoMapper.toDto(producto.get()))
        );
    }

    public ResponseEntity<ApiResponse<ProductoDTO>> crearProducto(ProductoDTO productoDTO) {
        Producto producto = productoMapper.toEntity(productoDTO);
        Producto guardado = productoRepository.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Producto creado exitosamente", productoMapper.toDto(guardado)));
    }

    public ResponseEntity<?> actualizarProducto(Long id, ProductoDTO productoDTO) {
        Optional<Producto> existente = productoRepository.findById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("El producto con id " + id + " no existe"));
        }

        Producto producto = existente.get();
        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setCantidadEnStock(productoDTO.getCantidadEnStock());

        Producto actualizado = productoRepository.save(producto);
        return ResponseEntity.ok(
                new ApiResponse<>("Producto actualizado exitosamente", productoMapper.toDto(actualizado))
        );
    }

    public ResponseEntity<ApiResponse<?>> eliminarProductoPorId(Long id) {
        Optional<Producto> existente = productoRepository.findById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("El producto con id " + id + " no existe"));
        }

        productoRepository.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>("Producto eliminado exitosamente"));
    }

    public ResponseEntity<?> asignarCategoria(Long productoId, Long categoriaId) {
        Optional<Producto> productoOpt = productoRepository.findById(productoId);
        if (productoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("El producto con id " + productoId + " no existe"));
        }

        Optional<Categoria> categoriaOpt = categoriaRepository.findById(categoriaId);
        if (categoriaOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("La categoría con id " + categoriaId + " no existe"));
        }

        Producto producto = productoOpt.get();
        producto.setCategoria(categoriaOpt.get());
        Producto actualizado = productoRepository.save(producto);
        return ResponseEntity.ok(
                new ApiResponse<>("Categoría asignada al producto exitosamente", productoMapper.toDto(actualizado))
        );
    }
}
