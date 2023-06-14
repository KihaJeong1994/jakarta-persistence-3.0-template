package com.example.jpatemplate.domain.project.entity;

import com.example.jpatemplate.domain.employee.entity.Employee;
import com.example.jpatemplate.domain.projectEmployee.entity.ProjectEmployee;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project {

    @Id
    private Long id;

    @OneToMany(
            mappedBy = "project",
            cascade = CascadeType.ALL,
            orphanRemoval = true // rows in join table is not going to be reused as the relationship is removed
    )
    private List<ProjectEmployee> employees;

    @Builder
    public Project(Long id, List<ProjectEmployee> employees) {
        this.id = id;
        this.employees = employees!=null ? employees : new ArrayList<>();
    }

    public void addEmployee(Employee employee){
        ProjectEmployee projectEmployee = new ProjectEmployee(this, employee);
        this.employees.add(projectEmployee);
        employee.getProjects().add(projectEmployee);
    }

    public void removeEmployee(Employee employee){
        ProjectEmployee projectEmployee = new ProjectEmployee(this, employee);
        this.employees.remove(projectEmployee);
        employee.getProjects().remove(projectEmployee);
        projectEmployee.setProject(null);
        projectEmployee.setEmployee(null);
    }


    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", employees=" + employees +
                '}';
    }
}
