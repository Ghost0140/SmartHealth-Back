package com.farmacia.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.*;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "smarthealth_exchange";
    public static final String ROUTING_KEY_RECETA = "receta.generada";
    public static final String QUEUE_NOTIFICACIONES = "notificaciones_queue";

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue notificacionesQueue() {
        return new Queue(QUEUE_NOTIFICACIONES, true);
    }

    @Bean
    public Binding bindingReceta(Queue notificacionesQueue, DirectExchange exchange) {
        return BindingBuilder
                .bind(notificacionesQueue)
                .to(exchange)
                .with(ROUTING_KEY_RECETA);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}