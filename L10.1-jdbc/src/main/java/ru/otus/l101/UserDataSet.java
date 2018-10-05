package ru.otus.l101;

public class UserDataSet extends DataSet {
    private String name;
    private short age;

    public UserDataSet(long id, String name, short age) {
        super(id);
        this.name = name;
        this.age = age;
    }

    public UserDataSet(String name, short age) {
        this.name = name;
        this.age = age;
    }
    public UserDataSet(){
        this.name = null;
        this.age = 0;

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
    public void setId(int id){
        super.setId(id);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(short age) {
        this.age = age;
    }
}
