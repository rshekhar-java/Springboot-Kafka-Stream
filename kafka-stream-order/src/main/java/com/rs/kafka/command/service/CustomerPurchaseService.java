package com.rs.kafka.command.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rs.kafka.api.request.CustomerPurchaseMobileRequest;
import com.rs.kafka.api.request.CustomerPurchaseWebRequest;
import com.rs.kafka.command.action.CustomerPurchaseAction;

@Service
public class CustomerPurchaseService {

	@Autowired
	private CustomerPurchaseAction action;

	public String createPurchaseMobile(CustomerPurchaseMobileRequest request) {
		return action.publishMobileToKafka(request);
	}

	public String createPurchaseWeb(CustomerPurchaseWebRequest request) {
		return action.publishWebToKafka(request);
	}
}
