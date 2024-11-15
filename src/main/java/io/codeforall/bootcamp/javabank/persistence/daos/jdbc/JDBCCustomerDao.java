package io.codeforall.bootcamp.javabank.persistence.daos.jdbc;

import io.codeforall.bootcamp.javabank.model.Customer;
import io.codeforall.bootcamp.javabank.persistence.daos.CustomerDao;

import java.util.List;

public class JDBCCustomerDao implements CustomerDao {
    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public Customer findById(Integer id) {
        return null;
    }

    @Override
    public Customer saveOrUpdate(Customer customer) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Customer findByUsername(String username) {
        return null;
    }

    @Override
    public Customer findByEmail(String email) {
        return null;
    }
}
