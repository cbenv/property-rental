package edu.neu.cs5500.fantastix.data;

import edu.neu.cs5500.fantastix.core.Renter;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class RenterDAO extends AbstractDAO<Renter> {

    public RenterDAO(SessionFactory factory) {
        super(factory);
    }

    public Renter create(Renter renter) {
        return persist(renter);
    }

    public Renter findOne(String email) {
        return get(email);
    }

    public List<Renter> findAll() {
        return list(namedQuery("edu.neu.cs5500.fantastix.core.Renter.findAll"));
    }

    public Renter update(Renter renter, String email) {
        Renter r = get(email);
        if (r != null) {
            r.setPassword(renter.getPassword());
            r.setName(renter.getName());
            return persist(r);
        }
        return null;
    }

    public int delete(String email) {
        return namedQuery("edu.neu.cs5500.fantastix.core.Renter.delete").setParameter("email", email).executeUpdate();
    }
}
