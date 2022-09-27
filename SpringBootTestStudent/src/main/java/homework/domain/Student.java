package homework.domain;


public class Student {
    private final String name;
    private final String surname;

    public Student(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }
}
