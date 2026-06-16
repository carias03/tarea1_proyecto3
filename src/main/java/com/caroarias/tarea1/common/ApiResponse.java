package com.caroarias.tarea1.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Formato estandar para todas las respuestas de la API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private String message;
    private T data;

    /** Respuesta sin data (para deletes o errores) */
    public ApiResponse(String message) {
        this.message = message;
        this.data = null;
    }
}
