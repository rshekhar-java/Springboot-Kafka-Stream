package com.rs.kafka.command.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rs.kafka.api.request.WebLayoutVoteRequest;
import com.rs.kafka.command.action.WebLayoutVoteAction;

@Service
public class WebLayoutVoteService {

	@Autowired
	private WebLayoutVoteAction action;

	public void createLayoutVote(WebLayoutVoteRequest request) {
		action.publishToKafka(request);
	}

}
