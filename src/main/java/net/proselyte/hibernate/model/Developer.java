package net.proselyte.hibernate.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "DEVELOPER")
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

    public Developer(String firstName, String lastName, BigDecimal salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }

    public Developer(Long id, String firstName, String lastName, BigDecimal salary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }

    @Id
    @Column(name = "ID_DEVELOPER")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "SALARY")
    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "PROJECT_DEVELOPER",
            joinColumns = { @JoinColumn(name = "ID_DEVELOPER", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "ID_PROJECT", nullable = false, updatable = false) }
    )
    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "DEVELOPER_SKILL",
            joinColumns = { @JoinColumn(name = "ID_DEVELOPER", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "ID_SKILL", nullable = false, updatable = false) }
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
                ", SALARY=" + salary +
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