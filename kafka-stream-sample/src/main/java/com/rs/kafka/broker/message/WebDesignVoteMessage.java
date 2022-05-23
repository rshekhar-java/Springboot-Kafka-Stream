package com.rs.kafka.broker.message;

public class WebDesignVoteMessage {

	private String color;
	private String layout;

	public String getColor() {
		return color;
	}

	public String getLayout() {
		return layout;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	@Override
	public String toString() {
		return "WebDesignVoteMessage [color=" + color + ", layout=" + layout + "]";
	}

}
