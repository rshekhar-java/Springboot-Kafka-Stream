package com.rs.kafka.util;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

import com.rs.kafka.broker.message.WebColorVoteMessage;

public class WebColorVoteTimestampExtractor implements TimestampExtractor {

	@Override
	public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {
		var message = (WebColorVoteMessage) record.value();

		return message != null ? LocalDateTimeUtil.toEpochTimestamp(message.getVoteDateTime()) : record.timestamp();
	}

}
