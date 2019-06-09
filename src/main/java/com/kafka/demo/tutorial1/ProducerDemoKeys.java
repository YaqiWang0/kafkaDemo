package com.kafka.demo.tutorial1;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class ProducerDemoKeys {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final Logger logger= LoggerFactory.getLogger(ProducerDemoKeys.class);
        //create Producer properties
        Properties properties=new Properties();
        String bootstrapServers="localhost:9092";
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        //what type of value you are sending to kafka
        //serialize to bytes
        //producer will convert what ever we sent to kafka in bytes
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

        //create the producer
        KafkaProducer<String,String> producer = new KafkaProducer<String, String>(properties);

        for(int i=0;i<10;i++) {

            String topic="first_topic";
            String value="hello world "+Integer.toString(i);
            String key="id_"+Integer.toString(i);
            //create a producer record
            ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, key,value);
            logger.info("Key: "+key);//log the key;
            //send data
            //asynchronous
            producer.send(record, new Callback() {
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    //execute everytime when a record is successful sent
                    if (e == null) {
                        //the record was successfully sent
                        logger.info("Received new metadata: \n" + "Topic:" + recordMetadata.topic() + "\n" + "Partition:" + recordMetadata.partition() + "\n" + recordMetadata.offset() + "\n" + "TimeStamp:" + recordMetadata.timestamp());
                    } else {
                        logger.error("Error while producing", e);
                    }
                }
            }).get();//block the .send() to make it synchronous -don't do this in production
        }

        //wait for the data to be sent
        //flush data
        producer.flush();
        //flush and close producer
        producer.close();

    }
}
