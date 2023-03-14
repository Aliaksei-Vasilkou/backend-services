package com.epam.mentoring;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

import static com.epam.mentoring.common.utils.KafkaConstants.BOOTSTRAP_SERVER_LOCATION;
import static com.epam.mentoring.common.utils.KafkaConstants.SCHEMA_REGISTRY_LOCATION;
import static com.epam.mentoring.common.utils.KafkaConstants.TOPIC_NAME;
import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;

public class KafkaClient {

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty(BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER_LOCATION);
        properties.setProperty(GROUP_ID_CONFIG, "consumer-group");
        properties.setProperty(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
        properties.setProperty("schema.registry.url", SCHEMA_REGISTRY_LOCATION);

        try (Consumer<String, GenericRecord> consumer = new KafkaConsumer<>(properties)) {
            consumer.subscribe(Collections.singletonList(TOPIC_NAME));

            while (true) {
                ConsumerRecords<String, GenericRecord> consumerRecords = consumer.poll(100);
                for (ConsumerRecord<String, GenericRecord> notificationRecord : consumerRecords) {
                    System.out.println(notificationRecord);
                }
            }

        }
    }
}
