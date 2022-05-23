package com.rs.kafka.util;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

import com.rs.kafka.broker.message.InventoryMessage;

public class InventoryTimestampExtractor implements TimestampExtractor {

	@Override
	public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {
		var inventoryMessage = (InventoryMessage) record.value();

		return inventoryMessage != null ? LocalDateTimeUtil.toEpochTimestamp(inventoryMessage.getTransactionTime())
				: record.timestamp();
	}

}
