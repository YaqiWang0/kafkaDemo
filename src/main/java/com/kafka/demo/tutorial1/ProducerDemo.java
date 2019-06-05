package com.kafka.demo.tutorial1;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProducerDemo {
    public static void main(String[] args) {
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

        //create a producer record
        ProducerRecord<String,String> record=new ProducerRecord<String, String>("first_topic","hello world");

        //send data
        //asynchronous
        producer.send(record);

        //wait for the data to be sent
        //flush data
        producer.flush();
        //flush and close producer
        producer.close();

    }
}
