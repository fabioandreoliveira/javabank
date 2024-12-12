package io.codeforall.bootcamp.javabank.services;

import io.codeforall.bootcamp.javabank.exceptions.AccountNotFoundException;
import io.codeforall.bootcamp.javabank.exceptions.CustomerNotFoundException;
import io.codeforall.bootcamp.javabank.exceptions.TransactionInvalidException;
import io.codeforall.bootcamp.javabank.persistence.model.account.Account;

/**
 * Common interface for account services, provides methods to manage accounts and perform account transactions
 */
public interface AccountService {

    /**
     * Gets the account with the given id
     *
     * @param id the account id
     * @return the account
     */
    Account get(Integer id);

    /**
     * Performs an {@link Account} deposit
     *
     * @param id         the account id
     * @param customerId the customer id
     * @param amount     the amount to deposit
     * @throws AccountNotFoundException
     * @throws CustomerNotFoundException
     * @throws TransactionInvalidException
     */
    void deposit(Integer id, Integer customerId, double amount)
            throws AccountNotFoundException, CustomerNotFoundException, TransactionInvalidException;

    /**
     * Perform an {@link Account} withdrawal
     *
     * @param id         the account id
     * @param customerId the customer id
     * @param amount     the amount to withdraw
     * @throws AccountNotFoundException
     * @throws CustomerNotFoundException
     * @throws TransactionInvalidException
     */
    void withdraw(Integer id, Integer customerId, double amount)
            throws AccountNotFoundException, CustomerNotFoundException, TransactionInvalidException;
}
