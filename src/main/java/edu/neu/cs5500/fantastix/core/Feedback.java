package edu.neu.cs5500.fantastix.core;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "feedback")
@NamedQueries(value = {
        @NamedQuery(
                name = "edu.neu.cs5500.fantastix.core.Feedback.findByProperty",
                query = "SELECT f FROM Feedback f WHERE f.propertyID = :propertyID"
        ),
        @NamedQuery(
                name = "edu.neu.cs5500.fantastix.core.Feedback.findByRenter",
                query = "SELECT f FROM Feedback f WHERE f.renterEmail = :renterEmail"
        ),
        @NamedQuery(
                name = "edu.neu.cs5500.fantastix.core.Feedback.findAverageRatingByProperty",
                query = "SELECT AVG(f.rating) FROM Feedback f WHERE f.propertyID = :propertyID"
        ),
        @NamedQuery(
                name = "edu.neu.cs5500.fantastix.core.Feedback.delete",
                query = "DELETE FROM Feedback f WHERE f.feedbackID = :feedbackID"
        )
})

public class Feedback
{
    @Id
    @Column(name = "`feedbackID`", nullable = false)
    private int feedbackID;

    @Column(name = "`propertyID`", nullable = false)
    private int propertyID;

    @Column(name = "`renterEmail`", nullable = false)
    private String renterEmail;

    @Column(name = "`rating`", nullable = false)
    private int rating;

    @Column(name = "`comment`")
    private String comment;

    @Column(name = "`timestamp`", nullable = false)
    private Date timestamp;

    public Feedback() {

    }

    public Feedback(int propertyID, String renterEmail, int rating, String comment, Date timestamp) {
        this.propertyID = propertyID;
        this.renterEmail = renterEmail;
        this.rating = rating;
        this.comment = comment;
        this.timestamp = timestamp;
    }

    public Feedback(int feedbackID, int propertyID, String renterEmail, int rating, String comment, Date timestamp) {
        this.feedbackID = feedbackID;
        this.propertyID = propertyID;
        this.renterEmail = renterEmail;
        this.rating = rating;
        this.comment = comment;
        this.timestamp = timestamp;
    }

    public int getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(int feedbackID) {
        this.feedbackID = feedbackID;
    }

    public int getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(int propertyID) {
        this.propertyID = propertyID;
    }

    public String getRenterEmail() {
        return renterEmail;
    }

    public void setRenterEmail(String renterEmail) {
        this.renterEmail = renterEmail;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
