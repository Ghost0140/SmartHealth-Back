package com.notificaciones.rabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CitaConsumer {
	
	 @PostConstruct
	    public void init() {
	        System.out.println("CitaConsumer cargado");
	    }

	@RabbitListener(queues = RabbitMQConfig.CITA_QUEUE)
	public void recibirCita(String mensaje) {

	    System.out.println("MENSAJE RECIBIDO");
	    System.out.println(mensaje);

	}
}