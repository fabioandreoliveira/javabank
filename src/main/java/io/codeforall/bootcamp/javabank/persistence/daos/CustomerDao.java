package io.codeforall.bootcamp.javabank.persistence.daos;

import io.codeforall.bootcamp.javabank.model.Customer;

import java.util.List;

public interface CustomerDao {
    // basic crud methods
    List<Customer> findAll();
    Customer findById(Integer id);
    Customer saveOrUpdate(Customer customer);
    void delete(Integer id);

    // additional methods
    Customer findByUsername(String username);
    Customer findByEmail(String email);

}

