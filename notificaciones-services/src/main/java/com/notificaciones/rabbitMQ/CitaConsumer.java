package com.notificaciones.rabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.notificaciones.service.NotificacionService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CitaConsumer {

    private final NotificacionService notificacionService;

    @PostConstruct
    public void init() {
        log.info("CitaConsumer cargado y escuchando cola: {}", RabbitMQConfig.CITA_QUEUE);
    }

    @RabbitListener(queues = RabbitMQConfig.CITA_QUEUE)
    public void recibirCita(CitaCreatedEvent event) {
    	 log.info("=================================");
         log.info("CITA RECIBIDA — ID: {}", event.idCita());
         log.info("Paciente ID: {} | Doctor ID: {}",event.idPaciente(),event.idDoctor());
         log.info("=================================");

         notificacionService.procesarCita(event);
    }
}
