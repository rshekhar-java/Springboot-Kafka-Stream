package com.rs.kafka.util;

import com.rs.kafka.broker.message.*;
import org.apache.commons.lang3.*;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.*;

import java.util.*;

/**
 * created by rs 5/13/2022.
 */
public class CommodityStreamUtil {
    public static OrderMessage maskCreditCard(OrderMessage original) {
        var converted = original.copy();
        var maskedCreditCardNumber = original.getCreditCardNumber().replaceFirst("\\d{12}",
                StringUtils.repeat('*', 12));

        converted.setCreditCardNumber(maskedCreditCardNumber);

        return converted;
    }

    public static OrderPatternMessage mapToOrderPattern(OrderMessage original) {
        var result = new OrderPatternMessage();

        result.setItemName(original.getItemName());
        result.setOrderDateTime(original.getOrderDateTime());
        result.setOrderLocation(original.getOrderLocation());
        result.setOrderNumber(original.getOrderNumber());
        result.setTotalItemAmount(original.getPrice() * original.getQuantity());

        return result;
    }

    public static OrderRewardMessage mapToOrderReward(OrderMessage original) {
        var result = new OrderRewardMessage();

        result.setItemName(original.getItemName());
        result.setOrderDateTime(original.getOrderDateTime());
        result.setOrderLocation(original.getOrderLocation());
        result.setOrderNumber(original.getOrderNumber());
        result.setPrice(original.getPrice());
        result.setQuantity(original.getQuantity());

        return result;
    }

    public static Predicate<String, OrderMessage> isLargeQuantity() {
        return (key, value) -> value.getQuantity() > 200;
    }

    public static Predicate<? super String, ? super OrderPatternMessage> isPlastic() {
        return (key, value) -> StringUtils.startsWithIgnoreCase(value.getItemName(), "plastic");
    }

    public static Predicate<? super String, ? super OrderMessage> isCheap() {
        return (key, value) -> value.getPrice() < 100;
    }

    public static KeyValueMapper<String, OrderMessage, String> generateStorageKey() {
        return (key, value) -> Base64.getEncoder().encodeToString(value.getOrderNumber().getBytes());
    }

    public static KeyValueMapper<String, OrderMessage, KeyValue<String, OrderRewardMessage>> mapToOrderRewardChangeKey() {
        return (key, value) -> KeyValue.pair(value.getOrderLocation(), mapToOrderReward(value));
    }

}
