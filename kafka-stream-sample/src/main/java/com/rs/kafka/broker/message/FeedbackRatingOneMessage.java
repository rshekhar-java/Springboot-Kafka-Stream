package com.rs.kafka.broker.message;

/**
 * created by rs 5/22/2022.
 */
public class FeedbackRatingOneMessage {
    private String location;
    private double averageRating;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    @Override
    public String toString() {
        return "FeedbackRatingOneMessage{" +
                "location='" + location + '\'' +
                ", averageRating=" + averageRating +
                '}';
    }
}
