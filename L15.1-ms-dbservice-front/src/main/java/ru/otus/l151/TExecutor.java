package ru.otus.l151;

import org.hibernate.Session;

import java.util.function.Function;

public interface TExecutor {
    <R> R runInSession(Function<Session, R> function);
}
