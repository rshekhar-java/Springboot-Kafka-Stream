package com.rs.kafka.broker.serde;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import org.apache.kafka.common.errors.*;
import org.apache.kafka.common.serialization.*;

/**
 * created by rs 5/11/2022.
 */
public class CustomJsonSerializer<T> implements Serializer<T> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, T data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new SerializationException(e.getMessage());
        }
    }
}
