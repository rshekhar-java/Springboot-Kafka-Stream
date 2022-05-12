package com.rs.kafka.config;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.*;
import org.apache.kafka.streams.*;
import org.springframework.context.annotation.*;
import org.springframework.kafka.annotation.*;
import org.springframework.kafka.config.*;

import java.util.*;

/**
 * created by rs 5/10/2022.
 */
@Configuration
@EnableKafkaStreams
public class KafkaStreamConfig {

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kafkaStreamsConfig() {
        var props = new HashMap<String, Object>();

        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafka-stream-" + System.currentTimeMillis());
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, "3000");
        props.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE_V2);
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");

        return new KafkaStreamsConfiguration(props);
    }

}
