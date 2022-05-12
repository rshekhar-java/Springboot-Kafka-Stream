package com.rs.kafka.broker.serde;

import com.fasterxml.jackson.databind.*;
import org.apache.kafka.common.errors.*;
import org.apache.kafka.common.serialization.*;

import java.io.*;

/**
 * created by rs 5/11/2022.
 */
public class CustomJsonDeserializer<T> implements Deserializer<T> {
    private ObjectMapper objectMapper = new ObjectMapper();

    private Class<T> deserializedClass;

    public CustomJsonDeserializer(Class<T> deserializedClass) {
        this.deserializedClass = deserializedClass;
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, deserializedClass);
        } catch (IOException e) {
            throw new SerializationException(e.getMessage());
        }
    }
}
