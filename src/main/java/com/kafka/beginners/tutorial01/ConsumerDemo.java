package com.kafka.beginners.tutorial01;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerDemo {

    private static final Logger LOG = LoggerFactory.getLogger(ProducerDemoWithKeys.class);

    private static final String BOOTSTRAP_SERVER = "127.0.0.1:9092";

    private static final String TOPIC_NAME = "first_topic";
    private static final String GROUP_ID = "my-second-app";
    private static final String AUTO_OFFSET_RESET_CONFIG_VALUE = "earliest";

    public static void main(String[] args) {

        //CREATE PRODUCER PROPERTIES
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        //set groupId
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, AUTO_OFFSET_RESET_CONFIG_VALUE);

        //Create Kafka Consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);

        //subscribe on messages
        consumer.subscribe(Arrays.asList(TOPIC_NAME));

        //poll
        //TODO - replace with the real world version
        while(true){
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

            for(ConsumerRecord<String, String> record: records){
                LOG.info("key - {}, value - {}", record.key(), record.value());
                LOG.info("partition - {}, offset - {}", record.partition(), record.offset());
            }
        }
    }
}
