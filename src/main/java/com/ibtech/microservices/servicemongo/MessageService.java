package com.ibtech.microservices.servicemongo;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibtech.microservices.servicemongo.data.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private RabbitTemplate rabbitTemplate;



    public MessageService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;

    }

    public void convertAndSend(Queue queue, Object objectToSend) {

        rabbitTemplate.convertAndSend(queue.getName(), serializeToJson(objectToSend));
    }

    private String serializeToJson(Object objectToSend) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";

        try {
            jsonInString = mapper.writeValueAsString(objectToSend);
        } catch (JsonProcessingException e) {
            logger.info(String.valueOf(e));
        }

        logger.info("Serialized message payload: {}", jsonInString);
        System.out.println(jsonInString);

        return jsonInString;
    }





}
