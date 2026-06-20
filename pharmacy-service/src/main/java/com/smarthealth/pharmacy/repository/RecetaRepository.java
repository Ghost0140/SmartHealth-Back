package com.smarthealth.pharmacy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smarthealth.pharmacy.entity.RecetaEntity;

public interface RecetaRepository extends JpaRepository<RecetaEntity, Long> {
	
	@Query("""
			SELECT r
			FROM RecetaEntity r
			JOIN FETCH r.medicamento
	""")
	List<RecetaEntity> findAllWithMedicamento();
	
	@Query("""
			SELECT r
			FROM RecetaEntity r
			JOIN FETCH r.medicamento
			WHERE r.idReceta = :id
	""")
	Optional<RecetaEntity> findByIdWithMedicamento(Long id);
	
	@Query("""
		    SELECT r
		    FROM RecetaEntity r
		    JOIN FETCH r.medicamento m
		    WHERE LOWER(m.nombre) LIKE LOWER(CONCAT('%', :medicamento, '%'))
	""")
	List<RecetaEntity> findByNombreWithNombre(String medicamento);

}
