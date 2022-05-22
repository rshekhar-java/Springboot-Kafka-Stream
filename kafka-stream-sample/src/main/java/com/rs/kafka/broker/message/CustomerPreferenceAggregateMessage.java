package com.rs.kafka.broker.message;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class CustomerPreferenceAggregateMessage {

	private Map<String, String> wishlistItems;

	private Map<String, String> shoppingCartItems;

	public CustomerPreferenceAggregateMessage() {
		this.wishlistItems = new HashMap<>();
		this.shoppingCartItems = new HashMap<>();
	}

	public Map<String, String> getShoppingCartItems() {
		return shoppingCartItems;
	}

	public Map<String, String> getWishlistItems() {
		return wishlistItems;
	}

	public void putShoppingCartItem(String itemName, LocalDateTime lastDateTime) {
		this.shoppingCartItems.put(itemName, DateTimeFormatter.ISO_DATE_TIME.format(lastDateTime));
	}

	public void putWishlistItem(String itemName, LocalDateTime lastDateTime) {
		this.wishlistItems.put(itemName, DateTimeFormatter.ISO_DATE_TIME.format(lastDateTime));
	}

	public void setShoppingCartItems(Map<String, String> shoppingCartItems) {
		this.shoppingCartItems = shoppingCartItems;
	}

	public void setWishlistItems(Map<String, String> wishlistItems) {
		this.wishlistItems = wishlistItems;
	}

	@Override
	public String toString() {
		return "CustomerPreferenceAggregateMessage [wishlistItems=" + wishlistItems + ", shoppingCartItems="
				+ shoppingCartItems + "]";
	}

}
