package ru.otus.l111;

import ru.otus.l111.dataSets.AddressDataSet;
import ru.otus.l111.dataSets.UserDataSet;

import java.util.List;

public interface DBService {
    String getMetaData();

    void insertUsers(UserDataSet... userDataSets);
    List<UserDataSet> getAllUsers();
    String getUserNameById(int id);
    UserDataSet getUserByInstance(UserDataSet userDataSet);
    List<UserDataSet> getUsersByAddress(AddressDataSet addressDataSet);

    void shutdown();
}
