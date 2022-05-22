package com.rs.kafka.broker.stream.flashsale;

import java.time.LocalDateTime;

import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;

import com.rs.kafka.broker.message.FlashSaleVoteMessage;
import com.rs.kafka.util.LocalDateTimeUtil;

public class FlashSaleVoteTwoValueTransformer implements ValueTransformer<FlashSaleVoteMessage, FlashSaleVoteMessage> {

	private final long voteStartTime;
	private final long voteEndTime;

	private ProcessorContext processorContext;

	public FlashSaleVoteTwoValueTransformer(LocalDateTime startDateTime, LocalDateTime endDateTime) {
		this.voteStartTime = LocalDateTimeUtil.toEpochTimestamp(startDateTime);
		this.voteEndTime = LocalDateTimeUtil.toEpochTimestamp(endDateTime);
	}

	@Override
	public void init(ProcessorContext context) {
		this.processorContext = context;
	}

	@Override
	public FlashSaleVoteMessage transform(FlashSaleVoteMessage value) {
		var recordTime = processorContext.timestamp();
		return (recordTime >= voteStartTime && recordTime <= voteEndTime) ? value : null;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
