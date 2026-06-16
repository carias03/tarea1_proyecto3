package com.caroarias.tarea1.negocio.mappers;

import com.caroarias.tarea1.negocio.models.dtos.ProductoDTO;
import com.caroarias.tarea1.negocio.models.entities.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public ProductoDTO toDto(Producto producto) {
        if (producto == null) {
            return null;
        }
        return new ProductoDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getCantidadEnStock(),
                producto.getCategoria() != null ? producto.getCategoria().getId() : null
        );
    }

    public Producto toEntity(ProductoDTO dto) {
        if (dto == null) {
            return null;
        }
        Producto producto = new Producto();
        producto.setId(dto.getId());
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setCantidadEnStock(dto.getCantidadEnStock());
        return producto;
    }
}
