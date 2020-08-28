package com.kafka.beginners.tutorial01;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class ProducerDemoWithKeys {

    private static final Logger LOG = LoggerFactory.getLogger(ProducerDemoWithKeys.class);

    private static final String BOOTSTRAP_SERVER = "127.0.0.1:9092";
    private static final String TOPIC_NAME = "first_topic";
    private static final String BASE_MSG = "Hello World! - ";
    private static final String BASE_KEY = "Key_";

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //CREATE PRODUCER PROPERTIES
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //CREATE THE PRODUCER
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        for (int i=0; i<10; i++){

            String key = BASE_KEY.concat(Integer.toString(i));
            String value = BASE_MSG.concat(Integer.toString(i));

            LOG.info("Key - {}", key);
            //CREATE A RECORD
            ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(TOPIC_NAME, key, value);

            //SEND MESSAGE - synchronous - DO NOT DO THIS IN PROD!!!
            producer.send(producerRecord, new Callback() {
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if(e!=null){
                        LOG.error("Error while producing", e);
                    }else{
                        LOG.info("Received new metadata. \n"+
                          "Partition : "+recordMetadata.partition() +
                          "\n Offset : " + recordMetadata.offset() +
                          "\n Timestamp : " + recordMetadata.timestamp()
                        );
                    }
                }
            }).get();
        }

        //force to flush data
        producer.flush();

        //flush and close producer
        producer.close();

    }
}
