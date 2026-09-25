package com.historial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.historial.model.HistorialClinico;

public interface IHistorialRepository
        extends JpaRepository<HistorialClinico, Integer> {

    List<HistorialClinico> findByIdPaciente(Integer idPaciente);
}