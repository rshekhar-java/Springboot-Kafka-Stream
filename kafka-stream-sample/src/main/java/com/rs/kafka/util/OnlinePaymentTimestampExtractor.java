package com.rs.kafka.util;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

import com.rs.kafka.broker.message.OnlinePaymentMessage;

public class OnlinePaymentTimestampExtractor implements TimestampExtractor {

	@Override
	public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {
		var onlinePaymentMessage = (OnlinePaymentMessage) record.value();

		return onlinePaymentMessage != null
				? LocalDateTimeUtil.toEpochTimestamp(onlinePaymentMessage.getPaymentDateTime())
				: record.timestamp();
	}

}
