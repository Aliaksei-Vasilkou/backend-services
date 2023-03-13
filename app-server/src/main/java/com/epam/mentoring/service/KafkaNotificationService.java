package com.epam.mentoring.service;

import com.epam.mentoring.domain.model.UserDto;
import com.epam.mentoring.kafka.avro.UserRegistrationNotification;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import jakarta.annotation.PreDestroy;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.UUID;

import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

@Log4j2
@Service
public class KafkaNotificationService {

    private static final String TOPIC_NAME = "user-registration-topic";
    private final KafkaProducer<String, UserRegistrationNotification> kafkaProducer;

    public KafkaNotificationService() {
        Properties properties = new Properties();
        properties.setProperty(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        properties.setProperty("schema.registry.url", "http://localhost:8081");

        this.kafkaProducer = new KafkaProducer<>(properties);
    }

    public void sendNotification(UserDto userDto) {
        UserRegistrationNotification notification = UserRegistrationNotification.newBuilder()
                .setFirstName(userDto.getFirstName())
                .setLastName(userDto.getLastName())
                .setEmail(userDto.getEmail())
                .build();

        ProducerRecord<String, UserRegistrationNotification> producerRecord =
                new ProducerRecord<>(TOPIC_NAME, UUID.randomUUID().toString(), notification);
        kafkaProducer.send(producerRecord, (metadata, exception) -> {
            if (exception == null) {
                log.info("Notification message successfully sent for {}", userDto);
                log.info("Topic: {}, Partition: {}, Offset: {}", metadata.topic(), metadata.partition(), metadata.offset());
            } else {
                log.error(exception.getStackTrace());
            }
        });
    }

    @PreDestroy
    public void shutDown() {
        log.info("Closing kafka connection");
        kafkaProducer.flush();
        kafkaProducer.close();
    }
}
