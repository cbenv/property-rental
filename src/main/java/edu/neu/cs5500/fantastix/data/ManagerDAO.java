package edu.neu.cs5500.fantastix.data;

import edu.neu.cs5500.fantastix.core.Manager;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class ManagerDAO extends AbstractDAO<Manager> {

    public ManagerDAO(SessionFactory factory) {
        super(factory);
    }

    public Manager create(Manager manager) {
        return persist(manager);
    }

    public Manager findOne(String email) {
        return get(email);
    }

    public List<Manager> findAll() {
        return list(namedQuery("edu.neu.cs5500.fantastix.core.Manager.findAll"));
    }

    public Manager update(Manager manager, String email) {
        Manager m = get(email);
        if (m != null) {
            m.setPassword(manager.getPassword());
            m.setName(manager.getName());
            m.setAddress(manager.getAddress());
            m.setCity(manager.getCity());
            m.setState(manager.getState());
            m.setZip(manager.getZip());
            return persist(m);
        }
        return null;
    }

    public int delete(String email) {
        return namedQuery("edu.neu.cs5500.fantastix.core.Manager.delete").setParameter("email", email).executeUpdate();
    }
}
