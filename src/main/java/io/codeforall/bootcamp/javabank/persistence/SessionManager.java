package io.codeforall.bootcamp.javabank.persistence;

import java.sql.Connection;

public interface SessionManager {
    public void startSession();
    public void stopSession();
    public Connection getCurrentSession();
}
