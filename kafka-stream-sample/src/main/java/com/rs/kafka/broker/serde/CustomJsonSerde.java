package com.rs.kafka.broker.serde;

import org.apache.kafka.common.serialization.*;

/**
 * created by rs 5/11/2022.
 */
public class CustomJsonSerde <T> implements Serde<T> {
    private CustomJsonSerializer<T> serializer;
    private CustomJsonDeserializer<T> deserializer;

    public CustomJsonSerde(CustomJsonSerializer<T> serializer, CustomJsonDeserializer<T> deserializer) {
        super();
        this.serializer = serializer;
        this.deserializer = deserializer;
    }

    @Override
    public Serializer<T> serializer() {
        return serializer;
    }

    @Override
    public Deserializer<T> deserializer() {
        return deserializer;
    }
}
