package com.ibtech.microservices.servicemongo;

import com.ibtech.microservices.servicemongo.data.Car;
import com.ibtech.microservices.servicemongo.data.CarRepository;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;

@Component
public class InitConfiguration {

    private static final Point PIVOTAL_OFFICE = new Point(-122.403664, 37.781825);
    private static final Point TREASURE_ISLAND = new Point(-122.370834, 37.820490);
    private static final Point EMERYVILLE = new Point(-122.287063, 37.834998);
    private static final Point FERRY_BUILDING = new Point(-122.391777, 37.790787);

    private static final Distance FIVE_MILES = new Distance(5, Metrics.MILES);

    private static final Car car1 = new Car("Honda", "Civic", 1997, EMERYVILLE);
    private static final Car car2 = new Car("Honda", "Accord", 2003, TREASURE_ISLAND);
    private static final Car car3 = new Car("Ford", "Escort", 1985, PIVOTAL_OFFICE);


    private MessageService messageService;
    private Queue carCreatedQueue;
    private Queue carDeletedQueue;
    private Queue carUpdatedQueue;


    @Autowired
    public InitConfiguration(MessageService messageService, @Qualifier("carUpdated") Queue carUpdatedQueue, @Qualifier("carCreated") Queue carCreatedQueue, @Qualifier("carDeleted") Queue carDeletedQueue){
        this.messageService = messageService;
        this.carCreatedQueue = carCreatedQueue;
        this.carDeletedQueue = carDeletedQueue;
        this.carUpdatedQueue = carUpdatedQueue;
    }

    @Bean
    public InitializingBean seedDatabase(CarRepository repository) {
        return () -> {
            repository.deleteAll();
            repository.save(car1);
            messageService.convertAndSend(carCreatedQueue, car1);
            repository.save(car2);
            messageService.convertAndSend(carCreatedQueue, car2);
            repository.save(car3);
            messageService.convertAndSend(carCreatedQueue, car3);
        };
    }

    @Bean
    public CommandLineRunner exampleQuery(CarRepository repository) {
        return (args) -> {
            repository.findByMakeIgnoringCase("HONDA").forEach(System.err::println);

            repository.findByPositionNear(FERRY_BUILDING, FIVE_MILES)
                    .forEach(System.err::println);
            ;
        };
    }


}
