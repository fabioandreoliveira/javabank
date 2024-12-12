package io.codeforall.bootcamp.javabank.factories;

import io.codeforall.bootcamp.javabank.persistence.model.account.AccountType;
import io.codeforall.bootcamp.javabank.persistence.model.account.CheckingAccount;
import io.codeforall.bootcamp.javabank.persistence.model.account.SavingsAccount;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class AccountFactoryTest {

    @Test
    public void testCreateAccount() throws Exception {

        //setup
        AccountFactory fakeFactory = spy(new AccountFactory());

        //exercise
        fakeFactory.createAccount(AccountType.CHECKING);
        fakeFactory.createAccount(AccountType.SAVINGS);

        //verify
        verify(fakeFactory, times(1)).createAccount(AccountType.CHECKING);
        verify(fakeFactory, times(1)).createAccount(AccountType.SAVINGS);
        assertTrue(fakeFactory.createAccount(AccountType.CHECKING) instanceof CheckingAccount);
        assertTrue(fakeFactory.createAccount(AccountType.SAVINGS) instanceof SavingsAccount);
    }

}
