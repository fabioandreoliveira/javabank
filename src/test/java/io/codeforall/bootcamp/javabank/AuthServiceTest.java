package io.codeforall.bootcamp.javabank;

import io.codeforall.bootcamp.javabank.model.Customer;
import io.codeforall.bootcamp.javabank.services.AuthServiceImpl;
import io.codeforall.bootcamp.javabank.services.CustomerService;
import io.codeforall.bootcamp.javabank.services.CustomerServiceImpl;

public class AuthServiceTest {

    public boolean test() {

        AuthServiceImpl authService = new AuthServiceImpl();
        CustomerService customerService = new CustomerServiceImpl();
        authService.setCustomerService(customerService);

        Customer customer = new Customer();
        customerService.add(customer);

        // should authenticate
        authService.authenticate(customer.getId());
        if (authService.getAccessingCustomer() != customer) {
            return false;
        }

        return true;
    }
}
