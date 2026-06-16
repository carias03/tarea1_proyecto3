package com.caroarias.tarea1.negocio.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {
    private Long id;
    private String nombre;
    private String descripcion;
}
