package by.bsu.db.bean;

import java.util.Objects;

public class Section extends Entity {
    private String name;
    private int judges;

    public Section() {
    }

    public Section(int id) {
        super(id);
    }

    public Section(int id, String name, int judges) {
        super(id);
        this.name = name;
        this.judges = judges;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getJudges() {
        return judges;
    }

    public void setJudges(int judges) {
        this.judges = judges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Section section = (Section) o;
        return judges == section.judges &&
                name.equals(section.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, judges);
    }

    @Override
    public String toString() {
        return "Section{" +
                "id='" + getId() + '\'' +
                "name='" + name + '\'' +
                ", judges=" + judges +
                '}';
    }
}
