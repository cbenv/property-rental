package edu.neu.cs5500.fantastix.data;

import edu.neu.cs5500.fantastix.core.PropertyImage;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class PropertyImageDAO extends AbstractDAO<PropertyImage> {

    public PropertyImageDAO(SessionFactory factory)
    {
        super(factory);
    }

    public PropertyImage create(PropertyImage propertyImage)
    {
        return persist(propertyImage);
    }

    public PropertyImage findOne(int imageID) {
        return get(imageID);
    }

    public List<PropertyImage> findByProperty(int propertyID) {
        return list(namedQuery("edu.neu.cs5500.fantastix.core.PropertyImage.findByProperty").setParameter("propertyID", propertyID));
    }

    public PropertyImage updateDescription(String description, int imageID) {
        PropertyImage pi = get(imageID);
        if (pi != null) {
            pi.setDescription(description);
            return persist(pi);
        }
        return null;
    }

    public int delete(int imageID) {
        return namedQuery("edu.neu.cs5500.fantastix.core.PropertyImage.delete").setParameter("imageID", imageID).executeUpdate();
    }
}