package com.rs.kafka.broker.message;

/**
 * created by rs 5/11/2022.
 */
public class OrderReplyMessage {
    private String replyMessage;

    public String getReplyMessage() {
        return replyMessage;
    }

    public void setReplyMessage(String replyMessage) {
        this.replyMessage = replyMessage;
    }

    @Override
    public String toString() {
        return "OrderReplyMessage{" +
                "replyMessage='" + replyMessage + '\'' +
                '}';
    }
}
