package ru.otus.l101;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@FunctionalInterface
public interface TResultHandler<T> {
    T handle(Statement statement) throws SQLException;
}
