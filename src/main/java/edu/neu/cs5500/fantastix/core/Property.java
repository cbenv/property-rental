package edu.neu.cs5500.fantastix.core;

import javax.persistence.*;

@Entity
@Table(name = "property")
@NamedQueries(value = {
        @NamedQuery(
                name = "edu.neu.cs5500.fantastix.core.Property.delete",
                query = "DELETE FROM Property WHERE propertyID = :propertyID"
        ),
        @NamedQuery(
                name = "edu.neu.cs5500.fantastix.core.Property.findByManager",
                query = "SELECT p FROM Property p WHERE p.managerEmail = :managerEmail"
        ),
        @NamedQuery(
                name = "edu.neu.cs5500.fantastix.core.Property.findByQuery",
                query = "SELECT p FROM Property p " +
                        "WHERE p.name LIKE :name AND " +
                        "p.price <= :price AND " +
                        "p.zip LIKE :zip"
        )
})

public class Property
{
    @Id
    @Column(name = "`propertyID`")
    private int propertyID;

    @Column(name = "`name`", nullable = false)
    private String name;

    @Column(name = "`managerEmail`", nullable = false)
    private String managerEmail;

    @Column(name = "`price`", nullable = false)
    private double price;

    @Column(name = "`address`", nullable = false)
    private String address;

    @Column(name = "`city`", nullable = false)
    private String city;

    @Column(name = "`state`", nullable = false)
    private String state;

    @Column(name = "`zip`", nullable = false)
    private String zip;

    @Column(name = "`accommodation`", nullable = false)
    private int accommodation;

    @Column(name = "`bathrooms`", nullable = false)
    private double bathrooms;

    @Column(name = "`beds`", nullable = false)
    private int beds;

    @Column(name = "`roomType`", nullable = false)
    private String roomType;

    public Property() {

    }

    public Property(String name, String managerEmail, double price, String address, String city, String state, String zip, int accommodation, double bathrooms, int beds, String roomType) {
        this.name = name;
        this.managerEmail = managerEmail;
        this.price = price;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.accommodation = accommodation;
        this.bathrooms = bathrooms;
        this.beds = beds;
        this.roomType = roomType;
    }

    public Property(int propertyID, String name, String managerEmail, double price, String address, String city, String state, String zip, int accommodation, double bathrooms, int beds, String roomType) {
        this.propertyID = propertyID;
        this.name = name;
        this.managerEmail = managerEmail;
        this.price = price;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.accommodation = accommodation;
        this.bathrooms = bathrooms;
        this.beds = beds;
        this.roomType = roomType;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(int propertyID) {
        this.propertyID = propertyID;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public int getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(int accommodation) {
        this.accommodation = accommodation;
    }

    public double getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(double bathrooms) {
        this.bathrooms = bathrooms;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
}
