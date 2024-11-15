package io.codeforall.bootcamp.javabank.persistence;

public interface TransactionManager {
    public void beginRead();
    public void beginWrite();
    public void commit();
    public void rollback();
}
