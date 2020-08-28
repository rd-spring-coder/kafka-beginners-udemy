package com.kafka.beginners.tutorial01;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemoWithCallbacks {

    private static final Logger LOG = LoggerFactory.getLogger(ProducerDemoWithCallbacks.class);

    private static final String BOOTSTRAP_SERVER = "127.0.0.1:9092";

    public static void main(String[] args) {

        //CREATE PRODUCER PROPERTIES
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //CREATE THE PRODUCER
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        for (int i=0; i<10; i++){
            //CREATE A RECORD
            ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>("first_topic", "Hello World!"+ Integer.toString(i));


            //SEND MESSAGE - asynchronous
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
            });
        }

        //force to flush data
        producer.flush();

        //flush and close producer
        producer.close();

    }
}
