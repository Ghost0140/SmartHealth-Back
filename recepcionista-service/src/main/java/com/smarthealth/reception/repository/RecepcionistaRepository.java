package com.smarthealth.reception.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smarthealth.reception.entity.RecepcionistaEntity;

public interface RecepcionistaRepository extends JpaRepository<RecepcionistaEntity, Integer>{

	boolean existsByDni(String dni);
	List<RecepcionistaEntity> findByActivo(Boolean activo);
}
