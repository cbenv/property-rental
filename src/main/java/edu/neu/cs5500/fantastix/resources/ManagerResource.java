package edu.neu.cs5500.fantastix.resources;

import edu.neu.cs5500.fantastix.core.*;
import edu.neu.cs5500.fantastix.data.*;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/manager")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(value = "/manager", description = "")
public class ManagerResource {

    private static ManagerDAO managerDAO;
    private static PropertyDAO propertyDAO;
    private static FeedbackDAO feedbackDAO;
    private static BookingDAO bookingDAO;
    private static PropertyImageDAO propertyImageDAO;

    public ManagerResource(ManagerDAO managerDAO, PropertyDAO propertyDAO, BookingDAO bookingDAO, FeedbackDAO feedbackDAO, PropertyImageDAO propertyImageDAO) {
        this.managerDAO = managerDAO;
        this.propertyDAO = propertyDAO;
        this.feedbackDAO = feedbackDAO;
        this.bookingDAO = bookingDAO;
        this.propertyImageDAO = propertyImageDAO;
    }

    @POST
    @ApiOperation(value = "Manager", notes = "Return the newly created manager", response = Manager.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Manager created")
    })
    @UnitOfWork
    public Response create(Manager manager) {
        int statusCode = 201;
        Manager r = managerDAO.create(manager);
        return Response.status(statusCode).entity(r).build();
    }

    @GET
    @Path("{email}")
    @ApiOperation(value = "Manager", notes = "Return a manager with the given email if exists, null otherwise", response = Manager.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Manager found"),
            @ApiResponse(code = 404, message = "No manager with such email found")
    })
    @UnitOfWork
    public Response findOne(@PathParam("email") String email) {
        Manager m = managerDAO.findOne(email);
        int statusCode = (m != null) ? 200 : 404;
        return Response.status(statusCode).entity(m).build();
    }

    @GET
    @ApiOperation(value = "Manager[]", notes = "Return all managers if any", response = Manager[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Managers found"),
            @ApiResponse(code = 404, message = "No managers registered")
    })
    @UnitOfWork
    public Response findAll() {
        List o = managerDAO.findAll();
        int n = o.size();
        Manager[] m = new Manager[n];
        for (int i = 0; i < n; i++) { m[i] = (Manager) o.get(i); }
        int statusCode = (m.length > 0) ? 200 : 404;
        return Response.status(statusCode).entity(m).build();
    }

    @PUT
    @Path("{email}")
    @ApiOperation(value = "Manager", notes = "Return updated renter if renter with given email exists, null otherwise", response = Manager.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Renter updated"),
            @ApiResponse(code = 404, message = "No renter with such email found")
    })
    @UnitOfWork
    public Response update(@PathParam("email") String email, Manager manager) {
        Manager m = managerDAO.update(manager, email);
        int statusCode = (m != null) ? 200 : 404;
        return Response.status(statusCode).entity(m).build();
    }

    @DELETE
    @Path("{email}")
    @ApiOperation(value = "void", notes = "Return nothing", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Manager deleted"),
            @ApiResponse(code = 404, message = "No manager with such email found")
    })
    @UnitOfWork
    public Response delete(@PathParam("email") String email) {
        int n = managerDAO.delete(email);
        int statusCode = (n != 0) ? 204 : 404;
        return Response.status(statusCode).build();
    }

    // PROPERTY

    @GET
    @Path("{email}/property")
    @ApiOperation(value = "Property[]", notes = "Return all properties by this manager if any", response = Property[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Properties found by the manager"),
            @ApiResponse(code = 404, message = "No properties managed by the manager")
    })
    @UnitOfWork
    public Response findProperties(@PathParam("email") String email) {
        List o = propertyDAO.findByManager(email);
        int n = o.size();
        Property[] p = new Property[n];
        for (int i = 0; i < n; i++) { p[i] = (Property) o.get(i); }
        int statusCode = (p.length > 0) ? 200 : 404;
        return Response.status(statusCode).entity(p).build();
    }

    @POST
    @Path("{email}/property")
    @ApiOperation(value = "Property", notes = "Return the newly managed property", response = Property.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Property managed")
    })
    @UnitOfWork
    public Response create(@PathParam("email") String email, Property property) {
        int statusCode = 201;
        property.setManagerEmail(email);
        Property p = propertyDAO.create(property);
        return Response.status(statusCode).entity(p).build();
    }

    @PUT
    @Path("{email}/property/{propertyID}")
    @ApiOperation(value = "Property", notes = "Return updated property if property managed by the manager with given ID exists, null otherwise", response = Property.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Property updated"),
            @ApiResponse(code = 404, message = "No property managed by the manager with such ID found")
    })
    @UnitOfWork
    public Response update(@PathParam("email") String email, @PathParam("propertyID") int propertyID, Property property) {
        int statusCode = 404;
        Property p = propertyDAO.findOne(propertyID);
        if (p != null && p.getManagerEmail().equals(email)) {
            p = propertyDAO.update(property, propertyID);
            statusCode = (p != null) ? 200 : 404;
        }
        return Response.status(statusCode).entity(p).build();
    }

    @DELETE
    @Path("{email}/property/{propertyID}")
    @ApiOperation(value = "void", notes = "Return nothing", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Property deleted"),
            @ApiResponse(code = 404, message = "No property with such ID found")
    })
    @UnitOfWork
    public Response delete(@PathParam("email") String email, @PathParam("propertyID") int propertyID) {
        int statusCode = 404;
        Property p = propertyDAO.findOne(propertyID);
        if (p != null && p.getManagerEmail().equals(email)) {
            int n = propertyDAO.delete(propertyID);
            statusCode = (n != 0) ? 204 : 404;
        }
        return Response.status(statusCode).build();
    }

    // IMAGE

    @POST
    @Path("{email}/property/{propertyID}/image")
    @ApiOperation(value = "PropertyImage", notes = "Return the newly uploaded image for this property managed by the manager", response = PropertyImage.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Image uploaded for this property"),
            @ApiResponse(code = 404, message = "No property managed by the manager is found")
    })
    @UnitOfWork
    public Response uploadImage(@PathParam("email") String email, @PathParam("propertyID") int propertyID, PropertyImage image) {
        int statusCode = 404;
        Property p = propertyDAO.findOne(propertyID);
        PropertyImage pi = null;
        if (p != null && p.getManagerEmail().equals(email)) {
            statusCode = 201;
            image.setPropertyID(propertyID);
            pi = propertyImageDAO.create(image);
        }
        return Response.status(statusCode).entity(pi).build();
    }

    @PUT
    @Path("{email}/property/{propertyID}/image/{imageID}")
    @ApiOperation(value = "PropertyImage", notes = "Return updated image if image of the property managed by the manager with given ID exists, null otherwise", response = PropertyImage.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Image description updated"),
            @ApiResponse(code = 404, message = "No image for this property managed by the manager with such ID found")
    })
    @UnitOfWork
    public Response updateImage(@PathParam("email") String email, @PathParam("propertyID") int propertyID, @PathParam("imageID") int imageID, PropertyImage propertyImage) {
        int statusCode = 404;
        Property p = propertyDAO.findOne(propertyID);
        PropertyImage pi = null;
        if (p != null && p.getManagerEmail().equals(email)) {
            pi = propertyImageDAO.findOne(imageID);
            if (pi != null && pi.getPropertyID() == propertyID) {
                pi = propertyImageDAO.updateDescription(propertyImage.getDescription(), imageID);
                statusCode = 200;
            }
        }
        return Response.status(statusCode).entity(pi).build();
    }

    @DELETE
    @Path("{email}/property/{propertyID}/image/{imageID}")
    @ApiOperation(value = "void", notes = "Return nothing", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Image deleted for this property managed by the manager"),
            @ApiResponse(code = 404, message = "No image for this property managed by the manager with such ID found")
    })
    @UnitOfWork
    public Response deleteImage(@PathParam("email") String email, @PathParam("propertyID") int propertyID, @PathParam("imageID") int imageID) {
        int statusCode = 404;
        Property p = propertyDAO.findOne(propertyID);
        PropertyImage pi;
        if (p != null && p.getManagerEmail().equals(email)) {
            pi = propertyImageDAO.findOne(imageID);
            if (pi != null && pi.getPropertyID() == propertyID) {
                int n = propertyImageDAO.delete(imageID);
                statusCode = (n != 0) ? 204 : 404;
            }
        }
        return Response.status(statusCode).build();
    }

    // FEEDBACK

    @GET
    @Path("{email}/property/{propertyID}/feedback")
    @ApiOperation(value = "Feedback[]", notes = "Return all feedbacks of the property if any", response = Feedback[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Feedbacks found for this property"),
            @ApiResponse(code = 404, message = "No feedbacks submitted for this property")
    })
    @UnitOfWork
    public Response findFeedbacks(@PathParam("propertyID") int propertyID) {
        List o = feedbackDAO.findByProperty(propertyID);
        int n = o.size();
        Feedback[] f = new Feedback[n];
        for (int i = 0; i < n; i++) { f[i] = (Feedback) o.get(i); }
        int statusCode = (f.length > 0) ? 200 : 404;
        return Response.status(statusCode).entity(f).build();
    }

    @GET
    @Path("{email}/property/feedback/{feedbackID}")
    @ApiOperation(value = "Feedback", notes = "Return a feedback of the property if exists, null otherwise", response = Feedback.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Feedback found for this property"),
            @ApiResponse(code = 404, message = "No feedback submitted for this property with such ID found")
    })
    @UnitOfWork
    public Response findFeedback(@PathParam("feedbackID") int feedbackID) {
        Feedback f = feedbackDAO.findOne(feedbackID);
        int statusCode = (f != null) ? 200 : 404;
        return Response.status(statusCode).entity(f).build();
    }

    // BOOKING
	
    @GET
    @Path("{email}/property/booking/{bookingID}")
    @ApiOperation(value = "Booking", notes = "Return a booking for the property if exists, null otherwise", response = Booking.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Booking found for this property"),
            @ApiResponse(code = 404, message = "No booking made for this property with such ID found")
    })
    @UnitOfWork
    public Response findBooking(@PathParam("bookingID") int bookingID) {
        Booking b = bookingDAO.findOne(bookingID);
        int statusCode = (b != null) ? 200 : 404;
        return Response.status(statusCode).entity(b).build();
    }

    @GET
    @Path("{email}/property/{propertyID}/booking")
    @ApiOperation(value = "Booking[]", notes = "Return all bookings for this property if any", response = Booking[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Bookings found for this property"),
            @ApiResponse(code = 404, message = "No bookings for this property")
    })
    @UnitOfWork
    public Response findBookings(@PathParam("propertyID") int propertyID) {
        List o = bookingDAO.findByProperty(propertyID);
        int n = o.size();
        Booking[] b = new Booking[n];
        for (int i = 0; i < n; i++) { b[i] = (Booking) o.get(i); }
        int statusCode = (b.length > 0) ? 200 : 404;
        return Response.status(statusCode).entity(b).build();
    }

    @PUT
    @Path("{email}/property/{propertyID}/booking/{bookingID}")
    @ApiOperation(value = "Booking", notes = "Return updated booking if booking for the property with given ID exists, null otherwise", response = Booking.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Booking updated"),
            @ApiResponse(code = 404, message = "No booking for this property with such ID found")
    })
    @UnitOfWork
    public Response updateBooking(@PathParam("propertyID") int propertyID, @PathParam("bookingID") int bookingID, Booking booking) {
        Booking b = bookingDAO.findOne(bookingID);
        if (b != null && b.getPropertyID() == propertyID) {
            b = bookingDAO.update(booking, bookingID);
            return Response.status(200).entity(b).build();
        }
        return Response.status(404).entity(null).build();
    }
}
