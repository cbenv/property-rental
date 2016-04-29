package edu.neu.cs5500.fantastix.core;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "booking")
@NamedQueries(value = {
        @NamedQuery(
                name = "edu.neu.cs5500.fantastix.core.Booking.findByProperty",
                query = "SELECT b FROM Booking b WHERE b.propertyID = :propertyID"
        ),
        @NamedQuery(
                name = "edu.neu.cs5500.fantastix.core.Booking.findByRenter",
                query = "SELECT b FROM Booking b WHERE b.renterEmail = :renterEmail"
        ),
        @NamedQuery(
                name = "edu.neu.cs5500.fantastix.core.Booking.findByStatus",
                query = "SELECT b FROM Booking b WHERE b.status = :status"
        )
})

public class Booking
{
    @Id
    @Column(name = "`bookingID`", nullable = false)
    private int bookingID;

    @Column(name = "`propertyID`", nullable = false)
    private int propertyID;

    @Column(name = "`renterEmail`", nullable = false)
    private String renterEmail;

    @Column(name = "`startDate`", nullable = false)
    private Date startDate;

    @Column(name = "`endDate`", nullable = false)
    private Date endDate;

    @Column(name = "`status`", nullable = false)
    private Status status;

    public Booking() {

    }

    public Booking(int propertyID, String renterEmail, Date startDate, Date endDate, Status status) {
        this.propertyID = propertyID;
        this.renterEmail = renterEmail;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public Booking(int bookingID, int propertyID, String renterEmail, Date startDate, Date endDate, Status status) {
        this.bookingID = bookingID;
        this.propertyID = propertyID;
        this.renterEmail = renterEmail;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}