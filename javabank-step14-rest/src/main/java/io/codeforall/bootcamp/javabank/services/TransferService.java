package io.codeforall.bootcamp.javabank.services;

import io.codeforall.bootcamp.javabank.domain.Transfer;
import io.codeforall.bootcamp.javabank.exceptions.AccountNotFoundException;
import io.codeforall.bootcamp.javabank.exceptions.CustomerNotFoundException;
import io.codeforall.bootcamp.javabank.exceptions.TransactionInvalidException;
import io.codeforall.bootcamp.javabank.persistence.model.account.Account;

/**
 * Common interface for transfer services, provides methods to perform account transfers
 */
public interface TransferService {

    /**
     * Performs a transfer between two {@link Account}
     *
     * @param transfer the transfer object
     * @throws AccountNotFoundException
     * @throws TransactionInvalidException
     */
    void transfer(Transfer transfer)
            throws AccountNotFoundException, TransactionInvalidException;

    /**
     * Performs a transfer between two {@link Account}, if possible
     *
     * @param transfer   the transfer object
     * @param customerId the customer id
     * @throws CustomerNotFoundException
     * @throws AccountNotFoundException
     * @throws TransactionInvalidException
     */
    void transfer(Transfer transfer, Integer customerId)
            throws CustomerNotFoundException, AccountNotFoundException, TransactionInvalidException;
}
