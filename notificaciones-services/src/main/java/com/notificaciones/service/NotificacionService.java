package com.notificaciones.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.notificaciones.model.Notificacion;
import com.notificaciones.rabbitMQ.CitaCreatedEvent;
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

    @Transactional
    public void procesarCita(CitaCreatedEvent event) {
        Notificacion notificacion = new Notificacion();

        notificacion.setIdUsuario(event.idPaciente());
        notificacion.setTipoEvento("CITA_CREADA");
        notificacion.setMensaje(
                "Nueva cita registrada. ID: "
                + event.idCita()
                + " Fecha: "
                + event.fecha()
        );
        notificacion.setLeido(false);
        notificacion.setFechaRegistro(LocalDateTime.now());
        notificacion.setActivo(true);

        repository.save(notificacion);
    }

    @Transactional
    public void procesarReceta(RecetaEvent event) {
        Notificacion notificacion = new Notificacion();

        notificacion.setIdUsuario(event.idPaciente());
        notificacion.setTipoEvento("RECETA_GENERADA");
        notificacion.setMensaje(
                "Receta generada. Medicamento: "
                        + event.nombreMedicamento()
                        + " Cantidad: "
                        + event.cantidad()
        );
        notificacion.setLeido(false);
        notificacion.setFechaRegistro(LocalDateTime.now());
        notificacion.setActivo(true);

        repository.save(notificacion);
    }
}
