package com.dave.test;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.common.config.SaslConfigs;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;


public class Consumer {
    private static final String BOOTSTRAP_SERVERS = "MY_HOST:9092";
    private static final String CLUSTER_KEY = "MY_KEY";
    private static final String CLUSTER_SECRET = "MY_SECRET";
    private static final String TOPIC = "MY_TOPIC";


    public static void main(String[] args) {
        Properties config = new Properties();
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "my-consumer-group");
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        config.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_SSL");
        config.put(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.plain.PlainLoginModule required username='" + CLUSTER_KEY + "' password='" + CLUSTER_SECRET + "';");
        config.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, Foo.class.getName());
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(JsonDeserializer.TRUSTED_PACKAGES, Consumer.class.getPackage().getName());
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());

        List<String> topics = new ArrayList<>();
        topics.add(TOPIC);
        KafkaConsumer consumer = new KafkaConsumer<>(config);
        try {
            consumer.subscribe(topics);

            while (true) {
                ConsumerRecords<String, Foo> records = consumer.poll(Duration.ofMillis(10_000L));
                for (ConsumerRecord<String, Foo> record : records) {
                    Foo foo = record.value();

                    // Do something with foo
                }
            }
        } catch (WakeupException e) {
            // ignore for shutdown
        } finally {
            consumer.close();
        }
    }
}
