package com.rs.kafka.util;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

import com.course.kafka.broker.message.OnlineOrderMessage;

public class OnlineOrderTimestampExtractor implements TimestampExtractor {

	@Override
	public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {
		var onlineOrderMessage = (OnlineOrderMessage) record.value();

		return onlineOrderMessage != null ? LocalDateTimeUtil.toEpochTimestamp(onlineOrderMessage.getOrderDateTime())
				: record.timestamp();
	}

}
