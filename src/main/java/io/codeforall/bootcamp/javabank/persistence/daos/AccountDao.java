package io.codeforall.bootcamp.javabank.persistence.daos;

import io.codeforall.bootcamp.javabank.model.account.Account;

import java.util.List;

public interface AccountDao {
    // basic crud methods
    List<Account> findAll();
    Account findById(Integer id);
    Account saveOrUpdate(Account account);
    void delete(Integer id);

    // additional methods
    Account findByUsername(String username);
    Account findByEmail(String email);

}

