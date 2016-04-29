package edu.neu.cs5500.fantastix.core;

import javax.persistence.*;

@Entity
@Table(name = "renter")
@NamedQueries(value = {
        @NamedQuery(
                name = "edu.neu.cs5500.fantastix.core.Renter.findOne",
                query = "SELECT r FROM Renter r WHERE r.email = :email"
        ),
        @NamedQuery(
                name = "edu.neu.cs5500.fantastix.core.Renter.findAll",
                query = "SELECT r FROM Renter r"
        ),
        @NamedQuery(
                name = "edu.neu.cs5500.fantastix.core.Renter.delete",
                query = "DELETE FROM Renter r WHERE r.email = :email"
        )
})

public class Renter {

    @Id
    @Column(name = "`email`", nullable = false)
    private String email;

    @Column(name = "`password`", nullable = false)
    private String password;

    @Column(name = "`name`", nullable = false)
    private String name;

    public Renter() {

    }

    public Renter(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
