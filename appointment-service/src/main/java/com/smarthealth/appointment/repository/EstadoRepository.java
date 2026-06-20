package com.smarthealth.appointment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smarthealth.appointment.entity.EstadoEntity;

public interface EstadoRepository extends JpaRepository<EstadoEntity, Integer> {
	
	Optional<EstadoEntity> findByNombreIgnoreCaseAndActivoTrue(String nombre);

}
