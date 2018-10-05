package ru.otus.l101;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ExecuteHandler<T> {
    T accept(PreparedStatement statement) throws SQLException;
}