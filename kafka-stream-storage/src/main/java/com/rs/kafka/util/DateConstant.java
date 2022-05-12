package com.rs.kafka.util;

import java.time.format.*;

/**
 * created by rs 5/11/2022.
 */
public interface DateConstant {
    String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DateConstant.DATE_TIME_FORMAT);

}
