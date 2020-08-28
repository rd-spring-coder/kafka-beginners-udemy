package com.kafka.beginners.tutorial01;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProducerDemo {

    public static final String BOOTSTRAP_SERVER = "127.0.0.1:9092";

    public static void main(String[] args) {

        //CREATE PRODUCER PROPERTIES
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //CREATE THE PRODUCER
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        //CREATE A RECORD
        ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>("first_topic", "Hello World!");


        //SEND MESSAGE - asynchronous
        producer.send(producerRecord);

        //force to flush data
        producer.flush();

        //flush and close producer
        producer.close();

    }
}
