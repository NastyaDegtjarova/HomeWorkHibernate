package net.proselyte.hibernate.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "developer")
public class Developer {


    private Long id;


    private String firstName;


    private String lastName;


    private BigDecimal salary;


    private List<Project> projects;


    private List<Skill> skills;

    public Developer() {
    }

    public Developer(Long id) {
        this.id = id;
    }

    public Developer(Long id, String firstName, String lastName, BigDecimal salary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }

    @Id
    @Column(name = "id_developer")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "salary")
    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "project_developer",
            joinColumns = { @JoinColumn(name = "id_developer", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "id_project", nullable = false, updatable = false) }
    )
    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "developer_skill",
            joinColumns = { @JoinColumn(name = "id_developer", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "id_skill", nullable = false, updatable = false) }
    )
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
                ", projects='" +  (
                projects == null
                        ? "[]"
                        :projects.stream().map(Project::getName).collect(Collectors.toList())) + '\'' +
                ", skills='" +  (
                skills == null
                        ? "[]"
                        :skills.stream().map(Skill::getSpecialty).collect(Collectors.toList())) + '\'' +
                ", lastName='" + lastName + '\'' +
                ", salary=" + salary +
                '}';
    }

    public Developer withId(Long developerId) {
        setId(developerId);
        return this;
    }

    public Developer withFirstName(String firstName) {
        setFirstName(firstName);
        return this;
    }

    public Developer withLastName(String lastName) {
        setLastName(lastName);
        return this;
    }

    public Developer withSalary(BigDecimal salary) {
        setSalary(salary);
        return this;
    }
}