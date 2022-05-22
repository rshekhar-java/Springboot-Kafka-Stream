package com.rs.kafka.broker.stream.feedback.rating;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;

import com.rs.kafka.broker.message.FeedbackMessage;
import com.rs.kafka.broker.message.FeedbackRatingTwoMessage;

public class FeedbackRatingTwoValueTransformer implements ValueTransformer<FeedbackMessage, FeedbackRatingTwoMessage> {

	private ProcessorContext processorContext;
	private final String stateStoreName;
	private KeyValueStore<String, FeedbackRatingTwoStoreValue> ratingStateStore;

	public FeedbackRatingTwoValueTransformer(String stateStoreName) {
		if (StringUtils.isEmpty(stateStoreName)) {
			throw new IllegalArgumentException("stateStoreName must not empty");
		}

		this.stateStoreName = stateStoreName;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init(ProcessorContext context) {
		this.processorContext = context;
		this.ratingStateStore = (KeyValueStore<String, FeedbackRatingTwoStoreValue>) this.processorContext
				.getStateStore(stateStoreName);
	}

	@Override
	public FeedbackRatingTwoMessage transform(FeedbackMessage value) {
		var storeValue = Optional.ofNullable(ratingStateStore.get(value.getLocation()))
				.orElse(new FeedbackRatingTwoStoreValue());
		var ratingMap = Optional.ofNullable(storeValue.getRatingMap()).orElse(new TreeMap<Integer, Long>());

		var currentRatingCount = Optional.ofNullable(ratingMap.get(value.getRating())).orElse(0l);
		var newRatingCount = currentRatingCount + 1;

		ratingMap.put(value.getRating(), newRatingCount);
		ratingStateStore.put(value.getLocation(), storeValue);

		var branchRating = new FeedbackRatingTwoMessage();
		branchRating.setLocation(value.getLocation());
		branchRating.setRatingMap(ratingMap);
		branchRating.setAverageRating(calculateAverage(ratingMap));

		return branchRating;
	}

	private double calculateAverage(Map<Integer, Long> ratingMap) {
		var sumRating = 0l;
		var countRating = 0l;

		for (var entry : ratingMap.entrySet()) {
			sumRating += entry.getKey() * entry.getValue();
			countRating += entry.getValue();
		}
		
		return Math.round((double) sumRating / countRating * 10d) / 10d;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
