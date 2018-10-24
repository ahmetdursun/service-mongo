package com.ibtech.microservices.servicemongo.event;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarEventConfig {

    @Bean(name = "carCreated")
    public Queue carCreatedQueue() {
        return new Queue("car_created_queue");
    }

    @Bean(name = "carUpdated")
    public Queue carUpdatedQueue() {
        return new Queue("car_updated_queue");
    }

    @Bean(name = "carDeleted")
    public Queue carDeletedQueue() {
        return new Queue("car_deleted_queue");
    }


    @Bean(name = "neo4jCar")
    public Queue neo4jCarUpdatedQueue() {
        return new Queue("neo4j_car_updated_queue");
    }
}
