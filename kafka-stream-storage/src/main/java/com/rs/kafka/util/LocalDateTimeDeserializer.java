package com.rs.kafka.util;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.*;

import java.io.*;
import java.time.*;

/**
 * created by rs 5/11/2022.
 */
public class LocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {

    private static final long serialVersionUID = 1L;

    public LocalDateTimeDeserializer() {
        super(LocalDate.class);
    }

    @Override
    public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return LocalDateTime.parse(parser.readValueAs(String.class), DateConstant.DATE_TIME_FORMATTER);
    }
}
