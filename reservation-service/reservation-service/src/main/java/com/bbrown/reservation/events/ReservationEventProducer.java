package com.bbrown.reservation.events;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * This service is responsible for publishing reservation events to Kafka.
 */
@Service
@RequiredArgsConstructor
public class ReservationEventProducer {

    // KafkaTemplate is used to send messages to Kafka topics
    private final KafkaTemplate<String, Object> kafkaTemplate;

    // Name of the Kafka topic where reservation events will be published
    private static final String TOPIC = "reservation.created";

    /**
     * Publishes a ReservationCreatedEvent to the Kafka topic.
     */
    public void publishReservationCreatedEvent(ReservationCreatedEvent event) {

        // Send the event to Kafka
        kafkaTemplate.send(TOPIC, event);

        // (Optional) You can log or print here for debugging
        System.out.println("Published reservation.created event: " + event);
    }
}