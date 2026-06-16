package com.caroarias.tarea1.negocio.mappers;

import com.caroarias.tarea1.negocio.models.dtos.CategoriaDTO;
import com.caroarias.tarea1.negocio.models.entities.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    public CategoriaDTO toDto(Categoria categoria) {
        if (categoria == null) {
            return null;
        }
        return new CategoriaDTO(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion()
        );
    }

    public Categoria toEntity(CategoriaDTO dto) {
        if (dto == null) {
            return null;
        }
        Categoria categoria = new Categoria();
        categoria.setId(dto.getId());
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        return categoria;
    }
}
