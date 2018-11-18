package ru.otus.l151.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.otus.l151.dataSets.AddressDataSet;
import ru.otus.l151.dataSets.UserDataSet;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDataSetDAO {
    private Session session;

    public UserDataSetDAO(Session session) {
        this.session = session;
    }

    public void insertUserDataSet(UserDataSet dataSet) {
        session.save(dataSet);
    }

    public UserDataSet read(long id) {
        return session.load(UserDataSet.class, id);
    }

    public UserDataSet read(UserDataSet userDataSet) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserDataSet> criteria = builder.createQuery(UserDataSet.class);
        Root<UserDataSet> from = criteria.from(UserDataSet.class);
        criteria.where(builder.equal(from.get("name"), userDataSet.getName()),
                builder.equal(from.get("age"), userDataSet.getAge()),
                builder.equal(from.get("phone").get("number"), userDataSet.getPhone().getNumber()),
                builder.equal(from.get("address").get("street"), userDataSet.getAddress().getStreet()));
        Query<UserDataSet> query = session.createQuery(criteria);
        return query.getSingleResult();
    }

    public List<UserDataSet> read(AddressDataSet addressDataSet) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserDataSet> criteria = builder.createQuery(UserDataSet.class);
        Root<UserDataSet> from = criteria.from(UserDataSet.class);
        criteria.where(builder.equal(from.get("address").get("street"), addressDataSet.getStreet()));
        Query<UserDataSet> query = session.createQuery(criteria);
        return query.list();
    }

    public List<UserDataSet> readAll() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserDataSet> criteria = builder.createQuery(UserDataSet.class);
        criteria.from(UserDataSet.class);
        return session.createQuery(criteria).list();
    }

    public int count(){
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserDataSet> criteria = builder.createQuery(UserDataSet.class);
        criteria.from(UserDataSet.class);
        return session.createQuery(criteria).list().size();
    }
}
