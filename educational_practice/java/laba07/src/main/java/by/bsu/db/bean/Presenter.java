package by.bsu.db.bean;

import java.util.Objects;

public class Presenter extends Entity {
    private String surname;
    private String name;
    private String patronymic;
    private Report report;

    public Presenter() {
    }

    public Presenter(String surname, String name, String patronymic, Report report) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.report = report;
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

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Presenter presenter = (Presenter) o;
        return surname.equals(presenter.surname) &&
                name.equals(presenter.name) &&
                patronymic.equals(presenter.patronymic) &&
                report.equals(presenter.report);
    }

    @Override
    public int hashCode() {
        return Objects.hash(surname, name, patronymic, report);
    }

    @Override
    public String toString() {
        return "Presenter{" +
                "surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", report=" + report.toString() +
                '}';
    }
}
