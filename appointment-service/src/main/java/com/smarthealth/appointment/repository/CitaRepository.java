package com.smarthealth.appointment.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smarthealth.appointment.entity.CitaEntity;

public interface CitaRepository extends JpaRepository<CitaEntity, Long> {
	
	@Query("""
			SELECT c
			FROM CitaEntity c
			JOIN FETCH c.estado
	""")
	List<CitaEntity> findAllWithEstado();

	@Query("""
			SELECT c
			FROM CitaEntity c
			JOIN FETCH c.estado
			WHERE c.idCita = :id
	""")
	Optional<CitaEntity> findByIdWithEstado(Long id);
	
	@Query("""
		    SELECT c
		    FROM CitaEntity c
		    JOIN FETCH c.estado
		    WHERE c.estado.nombre = :estado
	""")
	List<CitaEntity> findByEstadoWithEstado(String estado);
	
	@Query("""
			SELECT COUNT(c) > 0
			FROM CitaEntity c
			WHERE c.idDoctor = :idDoctor
			AND c.fecha = :fecha
			AND c.estado.nombre <> 'CANCELADA'
	""")
	boolean existsByIdDoctorAndFecha(Integer idDoctor, LocalDateTime fecha);
	
	@Query("""
	        SELECT c
	        FROM CitaEntity c
	        JOIN FETCH c.estado
	        WHERE c.idDoctor = :idDoctor
	""")
	List<CitaEntity> findByIdDoctorWithEstado(Integer idDoctor);
	
	@Query("""
	        SELECT c
	        FROM CitaEntity c
	        JOIN FETCH c.estado
	        WHERE c.idDoctor = :idDoctor
	        AND c.estado.nombre = :estado
	""")
	List<CitaEntity> findByIdDoctorAndEstadoWithEstado(Integer idDoctor, String estado);

}
