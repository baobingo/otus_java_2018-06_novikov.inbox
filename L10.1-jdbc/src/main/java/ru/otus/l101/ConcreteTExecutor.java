package ru.otus.l101;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConcreteTExecutor implements TExecutor {

    private final Connection connection;

    public ConcreteTExecutor(Connection connection) {
        this.connection = connection;
    }

    @Override
    public <T> T execQuery(String query, TResultHandler<T> handler) throws SQLException {
        try(Statement stmt = connection.createStatement()) {
            stmt.execute(query,Statement.RETURN_GENERATED_KEYS);
            return handler.handle(stmt);
        }
    }

    @Override
    public int execUpdate(String update) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate(update);
        }
    }
}
