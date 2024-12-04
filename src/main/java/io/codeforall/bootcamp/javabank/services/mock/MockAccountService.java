package io.codeforall.bootcamp.javabank.services.mock;

import io.codeforall.bootcamp.javabank.services.AccountService;
import io.codeforall.bootcamp.javabank.services.CustomerService;
import io.codeforall.bootcamp.javabank.exceptions.AccountNotFoundException;
import io.codeforall.bootcamp.javabank.exceptions.CustomerNotFoundException;
import io.codeforall.bootcamp.javabank.exceptions.TransactionInvalidException;
import io.codeforall.bootcamp.javabank.persistence.model.Customer;
import io.codeforall.bootcamp.javabank.persistence.model.account.Account;
import io.codeforall.bootcamp.javabank.persistence.model.account.SavingsAccount;

import java.util.Optional;

/**
 * A mock {@link AccountService} implementation
 */
public class MockAccountService extends AbstractMockService<Account> implements AccountService {

    private CustomerService customerService;

    /**
     * Sets the customer service
     *
     * @param customerService the customer service to set
     */
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * @see AccountService#get(Integer)
     */
    @Override
    public Account get(Integer id) {
        return modelMap.get(id);
    }

    /**
     * @see AccountService#deposit(Integer, Integer, double)
     */
    @Override
    public void deposit(Integer id, Integer customerId, double amount)
            throws CustomerNotFoundException, AccountNotFoundException, TransactionInvalidException {

        Customer customer = Optional.ofNullable(customerService.get(customerId))
                .orElseThrow(CustomerNotFoundException::new);

        Account account = Optional.ofNullable(modelMap.get(id))
                .orElseThrow(AccountNotFoundException::new);

        if (!account.getCustomer().getId().equals(customerId)) {
            throw new AccountNotFoundException();
        }

        if (!account.canCredit(amount)) {
            throw new TransactionInvalidException();
        }

        for (Account a : customer.getAccounts()) {
            if (a.getId().equals(id)) {
                a.credit(amount);
            }
        }

        account.credit(amount);
    }

    /**
     * @see AccountService#withdraw(Integer, Integer, double)
     */
    @Override
    public void withdraw(Integer id, Integer customerId, double amount)
            throws CustomerNotFoundException, AccountNotFoundException, TransactionInvalidException {

        Customer customer = Optional.ofNullable(customerService.get(customerId))
                .orElseThrow(CustomerNotFoundException::new);

        Account account = Optional.ofNullable(get(id))
                .orElseThrow(AccountNotFoundException::new);

        if (!account.getCustomer().getId().equals(customerId)) {
            throw new AccountNotFoundException();
        }

        // in UI the user cannot click on Withdraw so this is here just for safety
        if (account instanceof SavingsAccount) {
            throw new TransactionInvalidException();
        }

        // make sure transaction can be performed
        if (!account.canDebit(amount)) {
            throw new TransactionInvalidException();
        }

        for (Account a : customer.getAccounts()) {
            if (a.getId().equals(id)) {
                a.debit(amount);
            }
        }

        account.debit(amount);
    }
}
