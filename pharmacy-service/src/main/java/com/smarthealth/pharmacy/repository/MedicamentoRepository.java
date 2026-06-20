package com.smarthealth.pharmacy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smarthealth.pharmacy.entity.MedicamentoEntity;

public interface MedicamentoRepository extends JpaRepository<MedicamentoEntity, Integer> {

	boolean existsByNombre(String nombre);

	List<MedicamentoEntity> findByActivo(Boolean activo);
	
	boolean existsByNombreAndIdMedicamentoNot(String nombre, Integer id);

}
