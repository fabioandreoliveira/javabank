package io.codeforall.bootcamp.javabank.functions;

import io.codeforall.bootcamp.javabank.functions.requests.AccountInfoRequest;
import io.codeforall.bootcamp.javabank.functions.responses.AccountInfoResponse;
import io.codeforall.bootcamp.javabank.persistence.model.Customer;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AccountInfoFunction implements Function<AccountInfoRequest, AccountInfoResponse> {

    private Customer customer;

    public AccountInfoFunction(Customer customer) {
        this.customer = customer;
    }

    @Override
    public AccountInfoResponse apply(AccountInfoRequest accountInfoRequest) {
        List<AccountInfoResponse.Account> accountsInfo = customer.getAccounts()
                .stream()
                .map(account -> new AccountInfoResponse.Account(account.getId(), account.getBalance(), account.getAccountType().toString()))
                .toList();

        return new AccountInfoResponse(accountsInfo);

    }
}
