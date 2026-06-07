package com.smarthealth.citas_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smarthealth.citas_service.entity.CitaEntity;

public interface CitaRepository extends JpaRepository<CitaEntity, Integer> {

}
