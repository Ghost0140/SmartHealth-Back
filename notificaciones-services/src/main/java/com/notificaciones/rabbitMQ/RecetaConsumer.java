package com.notificaciones.rabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.notificaciones.service.NotificacionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class RecetaConsumer {/*

	 private final NotificacionService notificacionService;

	    @RabbitListener(queues = RabbitMQConfig.RECETA_QUEUE)
	    public void recibirReceta(RecetaEvent event) {

	        log.info("=================================");
	        log.info("RECETA RECIBIDA");
	        log.info("ID RECETA: {}", event.getIdReceta());
	        log.info("=================================");

	        notificacionService.procesarReceta(event);
	    }*/
}