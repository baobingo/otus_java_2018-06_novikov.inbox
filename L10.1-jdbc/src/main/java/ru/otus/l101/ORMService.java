package ru.otus.l101;

import java.util.List;

public interface ORMService {
    String getTableStructureByClass(java.lang.Class object);
    List<String> getInsertQueryByObject(Object... objects);
}
