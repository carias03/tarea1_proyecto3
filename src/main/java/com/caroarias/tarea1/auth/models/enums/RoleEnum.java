package com.caroarias.tarea1.auth.models.enums;

/**
 * Enum interno para referencias a roles en el codigo
 * El valor `dbName` es el nombre que se guarda en la columna `roles.name`
 * y el que se usa en anotaciones como @PreAuthorize("hasRole('SUPER-ADMIN-ROLE')").
 */
public enum RoleEnum {
    SUPER_ADMIN_ROLE("SUPER-ADMIN-ROLE"),
    USER("USER");

    private final String dbName;

    RoleEnum(String dbName) {
        this.dbName = dbName;
    }

    public String getDbName() {
        return dbName;
    }
}
