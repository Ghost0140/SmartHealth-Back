package com.smarthealth.appointment.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.smarthealth.appointment.event.CitaCreatedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CitaProducer {

	private final RabbitTemplate rabbitTemplate;

	public void publicarCita(CitaCreatedEvent cita) {
		rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, cita);
	}

}
