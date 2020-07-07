package ua.wismut.model;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "developers")
public class Developer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "specialty_id", nullable = false)
    private Specialty specialty;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "developer_skill", joinColumns = @JoinColumn(name = "developer_id"), inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private List<Skill> skills;

    public Developer() {

    }

    public Developer(Long id, String firstName, String lastName, Specialty specialty, List<Skill> skills) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialty = specialty;
        this.skills = skills;
    }

    public Developer(String firstName, String lastName, Specialty specialty, List<Skill> skills) {
        this(null, firstName, lastName, specialty, skills);
    }

    public Developer(String firstName, String lastName) {
        this(firstName, lastName, null, Collections.emptyList());
    }

    public Developer(Long id, String firstName, String lastName) {
        this(id, firstName, lastName, null, Collections.emptyList());
    }

    public Developer(Long id, String firstName, String lastName, String specialtyName) {
        this(id, firstName, lastName, new Specialty(specialtyName), Collections.emptyList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", specialty=" + specialty +
                ", skills=" + skills +
                '}';
    }
}
