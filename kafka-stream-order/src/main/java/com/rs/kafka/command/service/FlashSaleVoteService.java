package com.rs.kafka.command.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rs.kafka.api.request.FlashSaleVoteRequest;
import com.rs.kafka.command.action.FlashSaleVoteAction;

@Service
public class FlashSaleVoteService {

	@Autowired
	private FlashSaleVoteAction action;

	public void createFlashSaleVote(FlashSaleVoteRequest request) {
		action.publishToKafka(request);
	}

}
