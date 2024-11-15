package io.codeforall.bootcamp.javabank.persistence.jdbc;

import io.codeforall.bootcamp.javabank.persistence.TransactionManager;

import java.sql.SQLException;

public class JDBCTransactionManager implements TransactionManager {

    private JDBCSessionManager sm;

    public void beginRead() {
        sm.startSession();
    }

    public void beginWrite() {
        try {
            sm.getCurrentSession().setAutoCommit(false);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void commit() {
        try {
            if (!sm.getCurrentSession().getAutoCommit()) {
                sm.getCurrentSession().commit();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        sm.stopSession();
    }

    public void rollback() {
        try {
            if (!sm.getCurrentSession().getAutoCommit()) {
                sm.getCurrentSession().rollback();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        sm.stopSession();
    }
}