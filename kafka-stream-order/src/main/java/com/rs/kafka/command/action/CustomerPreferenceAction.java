package com.rs.kafka.command.action;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.course.kafka.api.request.CustomerPreferenceShoppingCartRequest;
import com.course.kafka.api.request.CustomerPreferenceWishlistRequest;
import com.course.kafka.broker.message.CustomerPreferenceShoppingCartMessage;
import com.course.kafka.broker.message.CustomerPreferenceWishlistMessage;
import com.course.kafka.broker.producer.CustomerPreferenceProducer;

@Component
public class CustomerPreferenceAction {

	@Autowired
	private CustomerPreferenceProducer producer;

	public void publishShoppingCart(CustomerPreferenceShoppingCartRequest request) {
		var message = new CustomerPreferenceShoppingCartMessage(request.getCustomerId(), request.getItemName(),
				request.getCartAmount(), LocalDateTime.now());

		producer.publishShoppingCart(message);
	}

	public void publishWishlist(CustomerPreferenceWishlistRequest request) {
		var message = new CustomerPreferenceWishlistMessage(request.getCustomerId(), request.getItemName(),
				LocalDateTime.now());

		producer.publishWishlist(message);
	}

}
