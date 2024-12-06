package io.codeforall.bootcamp.javabank.functions.responses;

import java.util.List;
import java.util.Objects;

public class AccountInfoResponse {

    private List<Account> accounts;

    public AccountInfoResponse(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public static class Account {
        private Integer accountNumber;
        private Double balance;
        private String type;

        public Account(Integer accountNumber, Double balance, String type) {
            this.accountNumber = accountNumber;
            this.balance = balance;
            this.type = type;
        }

        public Integer getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(Integer accountNumber) {
            this.accountNumber = accountNumber;
        }

        public Double getBalance() {
            return balance;
        }

        public void setBalance(Double balance) {
            this.balance = balance;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Account account = (Account) o;
            return Objects.equals(accountNumber, account.accountNumber) && Objects.equals(balance, account.balance) && Objects.equals(type, account.type);
        }

        @Override
        public int hashCode() {
            return Objects.hash(accountNumber, balance, type);
        }
    }
}
