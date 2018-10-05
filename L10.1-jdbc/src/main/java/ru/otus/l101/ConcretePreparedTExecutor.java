package ru.otus.l101;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConcretePreparedTExecutor implements PreparedTExecutor {

    private Connection connection;

    public ConcretePreparedTExecutor(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public <T> T execQuery(String query, ExecuteHandler executeHandler, TResultHandler<T> handler) throws SQLException {
        try(PreparedStatement stmt = getConnection().prepareStatement(query)) {
            ResultSet result = (ResultSet)executeHandler.accept(stmt);
            return handler.handle(result);
        }
    }

    @Override
    public <T> T execUpdate(String update, ExecuteHandler executeHandler) throws SQLException {
        try(PreparedStatement stmt = getConnection().prepareStatement(update)) {
                return (T)executeHandler.accept(stmt);
            }
        }
}
