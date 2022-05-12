package com.rs.kafka.util;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.std.*;

import java.io.*;
import java.time.*;

/**
 * created by rs 5/11/2022.
 */
public class LocalDateTimeSerializer extends StdSerializer<LocalDateTime> {
    private static final long serialVersionUID = 1L;

    public LocalDateTimeSerializer() {
        this(null);
    }

    public LocalDateTimeSerializer(Class<LocalDateTime> t) {
        super(t);
    }

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider arg2) throws IOException {
        gen.writeString(DateConstant.DATE_TIME_FORMATTER.format(value));
    }
}
