package com.notificaciones.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.notificaciones.model.Notificacion;
import com.notificaciones.rabbitMQ.CitaEvent;
import com.notificaciones.rabbitMQ.RecetaEvent;
import com.notificaciones.repository.NotificacionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificacionService {

    private final NotificacionRepository repository;

    public List<Notificacion> listar() {
        return repository.findAll();
    }

    public ResponseEntity<Notificacion> buscar(Integer id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Notificacion> marcarLeida(Integer id) {
        return repository.findById(id)
                .map(notificacion -> {
                    notificacion.setLeido(true);
                    notificacion.setFechaLectura(LocalDateTime.now());
                    repository.save(notificacion);
                    return ResponseEntity.ok(notificacion);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Void> eliminar(Integer id) {
        return repository.findById(id)
                .map(notificacion -> {
                    repository.delete(notificacion);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    public void procesarCita(CitaEvent event) {
        Notificacion notificacion = new Notificacion();

        notificacion.setIdUsuario(event.getIdPaciente());
        notificacion.setTipoEvento("CITA_CREADA");
        notificacion.setMensaje(
                "Nueva cita registrada. ID: "
                        + event.getIdCita()
                        + " Fecha: "
                        + event.getFechaCita()
        );
        notificacion.setLeido(false);
        notificacion.setFechaRegistro(LocalDateTime.now());
        notificacion.setActivo(true);

        repository.save(notificacion);
    }

    @Transactional
    public void procesarReceta(RecetaEvent event) {
        Notificacion notificacion = new Notificacion();

        // CORREGIDO: antes se usaba event.getIdCita() por error
        notificacion.setIdUsuario(event.getIdPaciente());
        notificacion.setTipoEvento("RECETA_GENERADA");
        notificacion.setMensaje(
                "Receta generada. Medicamento: "
                        + event.getNombreMedicamento()
                        + " Cantidad: "
                        + event.getCantidad()
        );
        notificacion.setLeido(false);
        notificacion.setFechaRegistro(LocalDateTime.now());
        notificacion.setActivo(true);

        repository.save(notificacion);
    }
}
