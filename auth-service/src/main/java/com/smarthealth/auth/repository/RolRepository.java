package com.smarthealth.auth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smarthealth.auth.entity.RolEntity;

public interface RolRepository extends JpaRepository<RolEntity, Integer> {
	
	Optional<RolEntity> findByNombre(String nombre);
	
	List<RolEntity> findByActivo(Boolean activo);
	
	Optional<RolEntity> findByIdRolAndActivoTrue(Integer id);

}
