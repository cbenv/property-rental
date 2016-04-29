package edu.neu.cs5500.fantastix.resources;

import edu.neu.cs5500.fantastix.core.Property;
import edu.neu.cs5500.fantastix.core.PropertyImage;
import edu.neu.cs5500.fantastix.data.PropertyDAO;
import edu.neu.cs5500.fantastix.data.PropertyImageDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/property")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(value = "/property", description = "")
public class PropertyResource {

    private static PropertyDAO propertyDAO;
    private static PropertyImageDAO propertyImageDAO;

    public PropertyResource(PropertyDAO propertyDAO, PropertyImageDAO propertyImageDAO) {
        this.propertyDAO = propertyDAO;
        this.propertyImageDAO = propertyImageDAO;
    }

    @GET
    @Path("{propertyID}")
    @ApiOperation(value = "Property", notes = "Return property with given ID if exists, null otherwise", response = Property.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Property found"),
            @ApiResponse(code = 404, message = "No property with such ID found")
    })
    @UnitOfWork
    public Response findOne(@PathParam("propertyID") int propertyID) {
        Property p = propertyDAO.findOne(propertyID);
        int statusCode = (p != null) ? 200 : 404;
        return Response.status(statusCode).entity(p).build();
    }

    @GET
    @ApiOperation(value = "Property[]", notes = "Return all properties that satisfy constraints", response = Property[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Properties found"),
            @ApiResponse(code = 404, message = "No properties that satisfy constraints")
    })
    @UnitOfWork
    public Response findByQuery(@QueryParam("name") String name, @QueryParam("price") double price, @QueryParam("zip") String zip) {
        List o = propertyDAO.findByQuery(name, price, zip);
        int n = o.size();
        Property[] p = new Property[n];
        for (int i = 0; i < n; i++) { p[i] = (Property) o.get(i); }
        int statusCode = (p.length > 0) ? 200 : 404;
        return Response.status(statusCode).entity(p).build();
    }

    @GET
    @Path("{propertyID}/image")
    @ApiOperation(value = "PropertyImage[]", notes = "Return all images of the property if any", response = PropertyImage[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Images found for this property"),
            @ApiResponse(code = 404, message = "No images uploaded for this property")
    })
    @UnitOfWork
    public Response findImages(@PathParam("propertyID") int propertyID) {
        List o = propertyImageDAO.findByProperty(propertyID);
        int n = o.size();
        PropertyImage[] pi = new PropertyImage[n];
        for (int i = 0; i < n; i++) { pi[i] = (PropertyImage) o.get(i); }
        int statusCode = (pi.length > 0) ? 200 : 404;
        return Response.status(statusCode).entity(pi).build();
    }

    @GET
    @Path("{propertyID}/image/{imageID}")
    @ApiOperation(value = "PropertyImage", notes = "Return an image of the property if exists, null otherwise", response = PropertyImage.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Image found for this property"),
            @ApiResponse(code = 404, message = "No image uploaded for this property with such ID found")
    })
    @UnitOfWork
    public Response findImage(@PathParam("propertyID") int propertyID, @PathParam("imageID") int imageID) {
        PropertyImage pi = propertyImageDAO.findOne(imageID);
        int statusCode = (pi != null && pi.getPropertyID() == propertyID) ? 200 : 404;
        return Response.status(statusCode).entity(pi).build();
    }
}