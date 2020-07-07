package ua.wismut.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "specialties")
public class Specialty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "specialty")
    private List<Developer> developers;

    public Specialty() {

    }

    public Specialty(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Specialty(Long id) {
        this(id, "", "");
    }

    public Specialty(String name, String description) {
        this(null, name, description);
    }

    public Specialty(String name) {
        this(name, "");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Specialty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
