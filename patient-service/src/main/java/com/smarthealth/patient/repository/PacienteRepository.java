package com.smarthealth.patient.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smarthealth.patient.entity.PacienteEntity;

public interface PacienteRepository extends JpaRepository<PacienteEntity, Integer> {
	
	boolean existsByDni(String dni);
	
	List<PacienteEntity> findByActivo(Boolean activo);

}
