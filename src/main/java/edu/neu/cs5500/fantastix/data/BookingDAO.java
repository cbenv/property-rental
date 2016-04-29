package edu.neu.cs5500.fantastix.data;

import edu.neu.cs5500.fantastix.core.Booking;

import edu.neu.cs5500.fantastix.core.Status;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import java.util.List;

public class BookingDAO extends AbstractDAO<Booking> {

    public BookingDAO(SessionFactory factory) {
        super(factory);
    }

    public Booking create(Booking booking) {
        booking.setStatus(Status.PENDING);
        return persist(booking);
    }

    public Booking findOne(int bookingID) {
        return get(bookingID);
    }

    public List<Booking> findByProperty(int propertyID) {
        return list(namedQuery("edu.neu.cs5500.fantastix.core.Booking.findByProperty").setParameter("propertyID", propertyID));
    }

    public List<Booking> findByRenter(String renterEmail) {
        return list(namedQuery("edu.neu.cs5500.fantastix.core.Booking.findByRenter").setParameter("renterEmail", renterEmail));
    }

    public List<Booking> findByStatus(Status status) {
        return list(namedQuery("edu.neu.cs5500.fantastix.core.Booking.findByStatus").setParameter("status", status));
    }

    public Booking update(Booking booking, int bookingID) {
        Booking b = get(bookingID);
        if (b != null) {
            b.setStatus(booking.getStatus());
            return persist(b);
        }
        return null;
    }
}