package com.smarthealth.doctor.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smarthealth.doctor.entity.EspecialidadEntity;

public interface EspecialidadRepository extends JpaRepository<EspecialidadEntity, Integer> {
	
	List<EspecialidadEntity> findByActivo(Boolean activo);
	
	Optional<EspecialidadEntity> findByIdEspecialidadAndActivoTrue(Integer id);

}
