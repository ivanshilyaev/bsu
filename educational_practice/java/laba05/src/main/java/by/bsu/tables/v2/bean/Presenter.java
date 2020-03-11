package by.bsu.tables.v2.bean;

import java.util.Objects;

public class Presenter {
    private String surname;
    private String name;
    private String patronymic;
    private String university;

    public Presenter(String surname) {
        this.surname = surname;
    }

    public Presenter(String surname, String name, String patronymic, String university) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.university = university;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Presenter presenter = (Presenter) o;
        return surname.equals(presenter.surname) &&
                name.equals(presenter.name) &&
                patronymic.equals(presenter.patronymic) &&
                university.equals(presenter.university);
    }

    @Override
    public int hashCode() {
        return Objects.hash(surname, name, patronymic, university);
    }

    @Override
    public String toString() {
        return surname;
    }
}
