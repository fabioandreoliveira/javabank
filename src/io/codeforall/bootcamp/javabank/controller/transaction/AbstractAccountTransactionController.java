package io.codeforall.bootcamp.javabank.controller.transaction;

import io.codeforall.bootcamp.javabank.controller.AbstractController;
import io.codeforall.bootcamp.javabank.model.Bank;

/**
 * A generic account transaction controller to be used as a base for concrete transaction controller implementations
 * @see AbstractController
 * @see AccountTransactionController
 */
public abstract class AbstractAccountTransactionController extends AbstractController implements AccountTransactionController {

    protected Bank bank;

    /**
     * Sets the bank
     *
     * @param bank the bank to set
     */
    public void setBank(Bank bank) {
        this.bank = bank;
    }
}
