package io.codeforall.bootcamp.javabank.functions;

import io.codeforall.bootcamp.javabank.functions.requests.CustomerInfoRequest;
import io.codeforall.bootcamp.javabank.functions.responses.CustomerInfoResponse;
import io.codeforall.bootcamp.javabank.persistence.model.Customer;
import io.codeforall.bootcamp.javabank.persistence.model.account.Account;

import java.util.function.Function;

public class CustomerInfoFunction implements Function<CustomerInfoRequest, CustomerInfoResponse> {

    private Customer customer;

    public CustomerInfoFunction(Customer customer) {
        this.customer = customer;
    }

    @Override
    public CustomerInfoResponse apply(CustomerInfoRequest customerInfoRequest) {
        return new CustomerInfoResponse(
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getAccounts().size(),
                customer.getAccounts().stream().map(Account::getBalance).reduce(0.0, Double::sum)
        );
    }
}
