package ru.otus.l101;

import java.sql.SQLException;

public interface PreparedTExecutor {
    <T> T execQuery(String query, ExecuteHandler executeHandler, TResultHandler<T> handler) throws SQLException;
    <T> T execUpdate(String update, ExecuteHandler executeHandler) throws SQLException;
}
