package com.farmacia.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MensajeProductor {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void enviarRecetaGenerada(RecetaEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY_RECETA,
                event
        );

        System.out.println("Evento enviado a RabbitMQ: " + event);
    }
}