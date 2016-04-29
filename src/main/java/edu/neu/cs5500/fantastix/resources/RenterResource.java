package edu.neu.cs5500.fantastix.resources;

import edu.neu.cs5500.fantastix.core.Booking;
import edu.neu.cs5500.fantastix.core.Feedback;
import edu.neu.cs5500.fantastix.core.Renter;
import edu.neu.cs5500.fantastix.data.BookingDAO;
import edu.neu.cs5500.fantastix.data.FeedbackDAO;
import edu.neu.cs5500.fantastix.data.RenterDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.print.Book;
import java.util.List;

@Path("/renter")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(value = "/renter", description = "")
public class RenterResource {

    private static RenterDAO renterDAO;
    private static BookingDAO bookingDAO;
    private static FeedbackDAO feedbackDAO;

    public RenterResource(RenterDAO renterDAO, BookingDAO bookingDAO, FeedbackDAO feedbackDAO) {
        this.renterDAO = renterDAO;
        this.bookingDAO = bookingDAO;
        this.feedbackDAO = feedbackDAO;
    }

    @POST
    @ApiOperation(value = "Renter", notes = "Return the newly created renter", response = Renter.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Renter created")
    })
    @UnitOfWork
    public Response create(Renter renter) {
        int statusCode = 201;
        Renter r = renterDAO.create(renter);
        return Response.status(statusCode).entity(r).build();
    }

    @GET
    @Path("{email}")
    @ApiOperation(value = "Renter", notes = "Return a renter with the given email if exists, null otherwise", response = Renter.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Renter found"),
            @ApiResponse(code = 404, message = "No renter with such email found")
    })
    @UnitOfWork
    public Response findOne(@PathParam("email") String email) {
        Renter r = renterDAO.findOne(email);
        int statusCode = (r != null) ? 200 : 404;
        return Response.status(statusCode).entity(r).build();
    }

    @GET
    @ApiOperation(value = "Renter[]", notes = "Return all renters if any", response = Renter[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Renters found"),
            @ApiResponse(code = 404, message = "No renters registered")
    })
    @UnitOfWork
    public Response findAll() {
        List o = renterDAO.findAll();
        int n = o.size();
        Renter[] r = new Renter[n];
        for (int i = 0; i < n; i++) { r[i] = (Renter) o.get(i); }
        int statusCode = (r.length > 0) ? 200 : 404;
        return Response.status(statusCode).entity(r).build();
    }

    @PUT
    @Path("{email}")
    @ApiOperation(value = "Renter", notes = "Return updated renter if renter with given email exists, null otherwise", response = Renter.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Renter updated"),
            @ApiResponse(code = 404, message = "No renter with such email found")
    })
    @UnitOfWork
    public Response update(@PathParam("email") String email, Renter renter) {
        Renter r = renterDAO.update(renter, email);
        int statusCode = (r != null) ? 200 : 404;
        return Response.status(statusCode).entity(r).build();
    }

    @DELETE
    @Path("{email}")
    @ApiOperation(value = "void", notes = "Return nothing", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Renter deleted"),
            @ApiResponse(code = 404, message = "No renter with such email found")
    })
    @UnitOfWork
    public Response delete(@PathParam("email") String email) {
        int n = renterDAO.delete(email);
        int statusCode = (n != 0) ? 204 : 404;
        return Response.status(statusCode).build();
    }

    // BOOKING

    @POST
    @Path("{email}/booking")
    @ApiOperation(value = "Booking", notes = "Return the newly created booking", response = Booking.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Booking created")
    })
    @UnitOfWork
    public Response book(@PathParam("email") String email, Booking booking) {
        booking.setRenterEmail(email);
        int statusCode = 201;
        Booking b = bookingDAO.create(booking);
        return Response.status(statusCode).entity(b).build();
    }

    @GET
    @Path("{email}/booking/{bookingID}")
    @ApiOperation(value = "Booking", notes = "Return a booking by this renter if exists, null otherwise", response = Booking.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Booking found by the renter"),
            @ApiResponse(code = 404, message = "No booking made by the renter with such ID found")
    })
    @UnitOfWork
    public Response findBooking(@PathParam("email") String email, @PathParam("bookingID") int bookingID) {
        Booking b = bookingDAO.findOne(bookingID);
        int statusCode = (b != null && b.getRenterEmail().equals(email)) ? 200 : 404;
        return Response.status(statusCode).entity(b).build();
    }

    @GET
    @Path("{email}/booking")
    @ApiOperation(value = "Booking[]", notes = "Return all bookings by this renter if any", response = Booking[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Bookings found by the renter"),
            @ApiResponse(code = 404, message = "No bookings made by the renter")
    })
    @UnitOfWork
    public Response findBookings(@PathParam("email") String email) {
        List o = bookingDAO.findByRenter(email);
        int n = o.size();
        Booking[] b = new Booking[n];
        for (int i = 0; i < n; i++) { b[i] = (Booking) o.get(i); }
        int statusCode = (b.length > 0) ? 200 : 404;
        return Response.status(statusCode).entity(b).build();
    }

    @PUT
    @Path("{email}/booking/{bookingID}")
    @ApiOperation(value = "Booking", notes = "Return updated booking if booking by this renter with given ID exists, null otherwise", response = Booking.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Booking updated"),
            @ApiResponse(code = 404, message = "No booking by this renter with such ID found")
    })
    @UnitOfWork
    public Response updateBooking(@PathParam("email") String email, @PathParam("bookingID") int bookingID, Booking booking) {
        Booking b = bookingDAO.findOne(bookingID);
        if (b != null && b.getRenterEmail().equals(email)) {
            b = bookingDAO.update(booking, bookingID);
            return Response.status(200).entity(b).build();
        }
        return Response.status(404).entity(null).build();
    }

    // FEEDBACK

    @POST
    @Path("{email}/feedback")
    @ApiOperation(value = "Feedback", notes = "Return the newly created feedback", response = Feedback.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Feedback created")
    })
    @UnitOfWork
    public Response submitFeedback(@PathParam("email") String email, Feedback feedback) {
        feedback.setRenterEmail(email);
        int statusCode = 201;
        Feedback f = feedbackDAO.create(feedback);
        return Response.status(statusCode).entity(f).build();
    }

    @GET
    @Path("{email}/feedback")
    @ApiOperation(value = "Feedback[]", notes = "Return all feedback by this renter if any", response = Feedback[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Feedback found by the renter"),
            @ApiResponse(code = 404, message = "No feedback submitted by the renter")
    })
    @UnitOfWork
    public Response findFeedbacks(@PathParam("email") String email) {
        List o = feedbackDAO.findByRenter(email);
        int n = o.size();
        Feedback[] f = new Feedback[n];
        for (int i = 0; i < n; i++) { f[i] = (Feedback) o.get(i); }
        int statusCode = (f.length > 0) ? 200 : 404;
        return Response.status(statusCode).entity(f).build();
    }

    @GET
    @Path("{email}/feedback/{feedbackID}")
    @ApiOperation(value = "Feedback", notes = "Return a feedback by this renter if exists, null otherwise", response = Feedback.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Feedback found by this renter"),
            @ApiResponse(code = 404, message = "No feedback submitted by this renter with such ID found")
    })
    @UnitOfWork
    public Response findFeedback(@PathParam("email") String email, @PathParam("feedbackID") int feedbackID) {
        Feedback f = feedbackDAO.findOne(feedbackID);
        int statusCode = (f != null && f.getRenterEmail().equals(email)) ? 200 : 404;
        return Response.status(statusCode).entity(f).build();
    }

    @PUT
    @Path("{email}/feedback/{feedbackID}")
    @ApiOperation(value = "Feedback", notes = "Return updated feedback if feedback by this renter with given ID exists, null otherwise", response = Feedback.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Feedback updated"),
            @ApiResponse(code = 404, message = "No feedback by this renter with such ID found")
    })
    @UnitOfWork
    public Response updateFeedback(@PathParam("email") String email, @PathParam("feedbackID") int feedbackID, Feedback feedback) {
        Feedback f = feedbackDAO.findOne(feedbackID);
        if (f != null && f.getRenterEmail().equals(email)) {
            f = feedbackDAO.update(feedback, feedbackID);
            return Response.status(200).entity(f).build();
        }
        return Response.status(404).entity(null).build();
    }

    @DELETE
    @Path("{email}/feedback/{feedbackID}")
    @ApiOperation(value = "void", notes = "Return nothing", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Feedback deleted for this property"),
            @ApiResponse(code = 404, message = "No feedback for this property with such ID found")
    })
    @UnitOfWork
    public Response deleteFeedback(@PathParam("email") String email, @PathParam("feedbackID") int feedbackID) {
        Feedback f = feedbackDAO.findOne(feedbackID);
        int statusCode = 404;
        if (f != null && f.getRenterEmail().equals(email)) {
            int n = feedbackDAO.delete(feedbackID);
            statusCode = (n != 0) ? 204 : 404;
        }
        return Response.status(statusCode).build();
    }
}
