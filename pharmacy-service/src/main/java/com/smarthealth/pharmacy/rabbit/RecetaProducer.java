package com.smarthealth.pharmacy.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.smarthealth.pharmacy.event.RecetaCreatedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RecetaProducer {

	private final RabbitTemplate rabbitTemplate;

	public void publicarReceta(RecetaCreatedEvent receta) {
		rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, receta);
	}

}
