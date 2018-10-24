package com.ibtech.microservices.servicemongo.event;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibtech.microservices.servicemongo.MessageService;
import com.ibtech.microservices.servicemongo.data.Car;
import com.ibtech.microservices.servicemongo.data.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler(Car.class)
public class CarEventHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private MessageService messageService;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    @Qualifier("neo4jCar")
    private Queue neo4jCarUpdatedQueue;


    //private Queue carCreatedQueue;
    //private Queue carDeletedQueue;
    //private Queue carUpdatedQueue;

    @Autowired
    public CarEventHandler(MessageService messageService) {
        this.messageService = messageService;

    }

    @HandleBeforeSave
    public void handleBeforeSaved(Car car) {
        Car oldCar = carRepository.findById(car.getId()).orElse(null);
        messageService.convertAndSend(neo4jCarUpdatedQueue, oldCar);
    }

    @HandleAfterSave
    public void handleAfterSaved(Car car) {
        messageService.convertAndSend(neo4jCarUpdatedQueue, car);
    }


    /*
    @HandleAfterCreate
    public void handleAfterCreated(Car car) {
        messageService.convertAndSend(carCreatedQueue, car);
    }

    @HandleAfterDelete
    public void handleAfterDeleted(Car car) {
        messageService.convertAndSend(carDeletedQueue, car);
    }

    */
}
