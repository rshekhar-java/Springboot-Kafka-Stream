package com.rs.kafka.command.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.kafka.api.request.CustomerPreferenceShoppingCartRequest;
import com.course.kafka.api.request.CustomerPreferenceWishlistRequest;
import com.course.kafka.command.action.CustomerPreferenceAction;

@Service
public class CustomerPreferenceService {

	@Autowired
	private CustomerPreferenceAction action;

	public void createShoppingCart(CustomerPreferenceShoppingCartRequest request) {
		action.publishShoppingCart(request);
	}

	public void createWishlist(CustomerPreferenceWishlistRequest request) {
		action.publishWishlist(request);
	}
}
