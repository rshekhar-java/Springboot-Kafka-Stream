package com.rs.kafka.command.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rs.kafka.api.request.OnlinePaymentRequest;
import com.rs.kafka.command.action.OnlinePaymentAction;

@Service
public class OnlinePaymentService {

	@Autowired
	private OnlinePaymentAction action;

	public void pay(OnlinePaymentRequest request) {
		action.publishPaymentToKafka(request);
	}

}
