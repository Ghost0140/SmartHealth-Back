package com.farmacia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.farmacia.model.Receta;

public interface IRecetaRepository extends JpaRepository<Receta, Integer> {
}