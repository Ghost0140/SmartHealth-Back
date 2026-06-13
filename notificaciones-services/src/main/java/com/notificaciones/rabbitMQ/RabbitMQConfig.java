package com.notificaciones.rabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	public static final String CITA_QUEUE = "cita_queue";
    public static final String EXCHANGE = "cita_exchange";
    public static final String ROUTING_KEY = "cita_routing_key";
    
    // RECETAS
    public static final String RECETA_QUEUE = "notificaciones_queue";
    public static final String RECETA_EXCHANGE = "smarthealth_exchange";
    public static final String RECETA_ROUTING_KEY = "receta.generada";

    @Bean
    public Queue citaQueue() {
        return new Queue(CITA_QUEUE,true);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(citaQueue())
                .to(exchange())
                .with(ROUTING_KEY);
    }
    
    @Bean
    public Queue recetaQueue() {
        return new Queue(RECETA_QUEUE, true);
    }

    @Bean
    public DirectExchange recetaExchange() {
        return new DirectExchange(RECETA_EXCHANGE);
    }

    @Bean
    public Binding recetaBinding() {
        return BindingBuilder
                .bind(recetaQueue())
                .to(recetaExchange())
                .with(RECETA_ROUTING_KEY);
    }


    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}