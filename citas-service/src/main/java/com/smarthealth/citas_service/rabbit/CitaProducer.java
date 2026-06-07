package com.smarthealth.citas_service.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.smarthealth.citas_service.event.CitaEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CitaProducer {

	private final RabbitTemplate rabbitTemplate;

	public void publicarCita(CitaEvent cita) {
		rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, cita);
	}

}
