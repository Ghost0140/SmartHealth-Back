package com.smarthealth.doctor.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smarthealth.doctor.entity.DoctorEntity;

public interface DoctorRepository extends JpaRepository<DoctorEntity, Integer> {

	boolean existsByDni(String dni);

	@Query("""
			SELECT d
			FROM DoctorEntity d
			JOIN FETCH d.especialidad
	""")
	List<DoctorEntity> findAllWithEspecialidad();

	@Query("""
			SELECT d
			FROM DoctorEntity d
			JOIN FETCH d.especialidad
			WHERE d.idDoctor = :id
	""")
	Optional<DoctorEntity> findByIdWithEspecialidad(Integer id);
	
	@Query("""
		    SELECT d
		    FROM DoctorEntity d
		    JOIN FETCH d.especialidad
		    WHERE d.activo = :activo
	""")
	List<DoctorEntity> findByActivoWithEspecialidad(Boolean activo);

}
