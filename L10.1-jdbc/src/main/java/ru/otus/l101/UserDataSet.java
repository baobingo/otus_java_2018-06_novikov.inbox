package ru.otus.l101;

public class UserDataSet extends DataSet {
    private final String name;
    private final short age;

    public UserDataSet(long id, String name, short age) {
        super(id);
        this.name = name;
        this.age = age;
    }

    public UserDataSet(String name, short age) {
        this.name = name;
        this.age = age;
    }

    public long getId(){
        return super.getId();
    }

    public String getName() {
        return name;
    }

    public short getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "UsersDataSet{" +
                "id=" + super.getId() +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
