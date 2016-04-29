package edu.neu.cs5500.fantastix.core;

import javax.persistence.*;

@Entity
@Table(name = "property_image")
@NamedQueries(value = {
        @NamedQuery(
                name = "edu.neu.cs5500.fantastix.core.PropertyImage.findByProperty",
                query = "SELECT pi FROM PropertyImage pi WHERE pi.propertyID = :propertyID"
        ),
        @NamedQuery(
                name = "edu.neu.cs5500.fantastix.core.PropertyImage.delete",
                query = "DELETE FROM PropertyImage pi WHERE pi.imageID = :imageID"
        )
})

public class PropertyImage
{
    @Id
    @Column(name = "`imageID`", nullable = false)
    private int imageID;

    @Column(name = "`propertyID`", nullable = false)
    private int propertyID;

    @Column(name = "`title`")
    private String title;

    @Column(name = "`description`")
    private String description;

    @Column(name = "`url`", nullable = false)
    private String url;

    public PropertyImage() {

    }

    public PropertyImage(int propertyID, String title, String description, String url) {
        this.propertyID = propertyID;
        this.title = title;
        this.description = description;
        this.url = url;
    }

    public PropertyImage(int imageID, int propertyID, String title, String description, String url) {
        this.imageID = imageID;
        this.propertyID = propertyID;
        this.title = title;
        this.description = description;
        this.url = url;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public int getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(int propertyID) {
        this.propertyID = propertyID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
