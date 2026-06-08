package com.farmacia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.farmacia.model.Medicamento;

public interface IMedicamentoRepository extends JpaRepository<Medicamento, Integer> {
}