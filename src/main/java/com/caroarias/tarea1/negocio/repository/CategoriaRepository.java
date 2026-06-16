package com.caroarias.tarea1.negocio.repository;

import com.caroarias.tarea1.negocio.models.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
