package com.ck.springdemo.dao;

import com.ck.springdemo.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

    // Need to inject the session factory
    // so DAO can use it to talk to db
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Customer> getCustomers() {
        // Get the current hibernate session
        Session session = sessionFactory.getCurrentSession();
        // Create a query
        Query<Customer> query = session.createQuery("from Customer order by lastName", Customer.class);
        // Execute query and get result list
        return query.getResultList();
    }

    @Override
    public void saveCustomer(Customer customer) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(customer);
    }

    @Override
    public Customer getCustomer(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Customer.class, id);
    }

    @Override
    public void deleteCustomer(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from Customer where id=:customerId");
        query.setParameter("customerId", id);
        query.executeUpdate();
    }

    @Override
    public List<Customer> searchCustomers(String theSearchName) {

        Session session = sessionFactory.getCurrentSession();
        Query query = null;
        if (theSearchName != null && theSearchName.trim().length() > 0) {
            // search for firstName or lastName .. case insensitive
            query = session.createQuery("from Customer " +
                            "where lower(firstName) like:theName " +
                            "or lower(lastName) like:theName",
                    Customer.class);
            query.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");
        } else {
            // theSearchName is empty .. so just get all customers
            query = session.createQuery("from Customer", Customer.class);
        }
        return (List<Customer>) query.getResultList();
    }
}
