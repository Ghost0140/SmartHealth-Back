package com.notificaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.notificaciones.model.Notificacion;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion,Integer>{

}