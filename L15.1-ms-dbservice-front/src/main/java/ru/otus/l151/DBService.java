package ru.otus.l151;

import ru.otus.l151.dataSets.AddressDataSet;
import ru.otus.l151.dataSets.UserDataSet;
import java.util.List;


public interface DBService {
    String getMetaData();

    void insertUsers(UserDataSet... userDataSets);
    List<UserDataSet> getAllUsers();
    String getUserNameById(int id);
    UserDataSet getUserByExample(UserDataSet userDataSet);
    List<UserDataSet> getUsersByAddress(AddressDataSet addressDataSet);
    int count();

    void shutdown();
}
