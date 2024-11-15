package io.codeforall.bootcamp.javabank.services.jdbc;

import io.codeforall.bootcamp.javabank.factories.AccountFactory;
import io.codeforall.bootcamp.javabank.persistence.ConnectionManager;
import io.codeforall.bootcamp.javabank.model.account.Account;
import io.codeforall.bootcamp.javabank.model.account.AccountType;
import io.codeforall.bootcamp.javabank.services.AccountService;

import java.sql.*;

public class JdbcAccountService implements AccountService {

    private ConnectionManager connectionManager;
    private AccountFactory accountFactory;

    public JdbcAccountService(ConnectionManager connectionManager, AccountFactory accountFactory) {
        this.connectionManager = connectionManager;
        this.accountFactory = accountFactory;
    }

    @Override
    public Account get(Integer id) {

        Connection connection = connectionManager.getConnection();
        Account account = null;

        try {

            String query = "SELECT id, account_type, customer_id, balance, version FROM account WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                AccountType accountType = AccountType.valueOf(resultSet.getString("account_type"));

                account = accountFactory.createAccount(accountType);
                account.setId(resultSet.getInt("id"));
                account.setCustomerId(resultSet.getInt("customer_id"));
                account.credit(resultSet.getInt("balance"));
                account.setVersion(resultSet.getInt("version"));
            }

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return account;
    }

    @Override
    public void add(Account account) {

        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = null;

        try {
            connection.setAutoCommit(false);

            String query = "INSERT INTO account(account_type, balance, customer_id) " +
                    "VALUES (?, ?, ?)";

            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, account.getAccountType().name());
            statement.setDouble(2, account.getBalance());
            statement.setInt(3, account.getCustomerId());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                account.setId(generatedKeys.getInt(1));
            }

            statement.close();

            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
                e.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void deposit(int id, double amount) {

        Connection connection = connectionManager.getConnection();
        Account account = null;

        try {
            connection.setAutoCommit(false);

            account = get(id);

            if (account == null) {
                throw new IllegalArgumentException("invalid account id");
            }

            account.credit(amount);

            updateBalance(account.getId(), account.getBalance(), account.getVersion());

            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void withdraw(int id, double amount) {

        Connection connection = connectionManager.getConnection();

        try {
            connection.setAutoCommit(false);

            Account account = get(id);

            if (account == null) {
                throw new IllegalArgumentException("invalid account id");
            }

            account.debit(amount);

            updateBalance(account.getId(), account.getBalance(), account.getVersion());

            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void transfer(int srcId, int dstId, double amount) {

        Connection connection = connectionManager.getConnection();

        try {
            connection.setAutoCommit(false);

            Account srcAccount = get(srcId);
            Account dstAccount = get(dstId);

            if (srcAccount == null || dstAccount == null) {
                throw new IllegalArgumentException("invalid account id");
            }

            if (srcAccount.canDebit(amount)) {

                srcAccount.debit(amount);
                dstAccount.credit(amount);

                updateBalance(srcAccount.getId(), srcAccount.getBalance(), srcAccount.getVersion());
                updateBalance(dstAccount.getId(), dstAccount.getBalance(), dstAccount.getVersion());

                connection.commit();

            } else {
                connection.rollback();
            }

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    private void updateBalance(int id, double totalBalance, int version) throws SQLException {

        String query = "UPDATE account SET balance = ?, version = ? WHERE id = ?";

        PreparedStatement statement = connectionManager.getConnection().prepareStatement(query);

        statement.setDouble(1, totalBalance);
        statement.setInt(2, version + 1);
        statement.setInt(3, id);

        statement.executeUpdate();
        statement.close();
    }
}
