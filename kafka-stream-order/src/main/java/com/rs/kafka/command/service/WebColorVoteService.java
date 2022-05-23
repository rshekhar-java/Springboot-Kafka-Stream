package com.rs.kafka.command.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.kafka.api.request.WebColorVoteRequest;
import com.course.kafka.command.action.WebColorVoteAction;

@Service
public class WebColorVoteService {

	@Autowired
	private WebColorVoteAction action;

	public void createColorVote(WebColorVoteRequest request) {
		action.publishToKafka(request);
	}

}
