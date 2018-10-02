package ru.otus.l101;

import java.sql.SQLException;

public interface TExecutor {
    <T> T execQuery(String query, TResultHandler<T> handler) throws SQLException;
    int execUpdate(String update) throws SQLException;
}
