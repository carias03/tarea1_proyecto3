package com.caroarias.tarea1.negocio.repository;

import com.caroarias.tarea1.negocio.models.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    boolean existsByCategoriaId(Long categoriaId);
}
