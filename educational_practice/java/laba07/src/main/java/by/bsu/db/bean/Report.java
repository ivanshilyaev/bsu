package by.bsu.db.bean;

import java.util.Objects;

public class Report extends Entity {
    private String name;
    private Section section;

    public Report() {
    }

    public Report(int id) {
        super(id);
    }

    public Report(int id, String name, Section section) {
        super(id);
        this.name = name;
        this.section = section;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return name.equals(report.name) &&
                Objects.equals(section, report.section);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, section);
    }

    @Override
    public String toString() {
        return "Report{" +
                "id='" + getId() + '\'' +
                "name='" + name + '\'' +
                ", section=" + section.toString() +
                '}';
    }
}
