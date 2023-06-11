package com.example.jpatemplate.employee.entity;

import com.example.jpatemplate.projectEmployee.entity.ProjectEmployee;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Employee {

    @Id
    private Long id;

    private String name;

    @OneToMany(mappedBy = "employee",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ProjectEmployee> projects;

    @Builder
    public Employee(Long id, String name, List<ProjectEmployee> projects) {
        this.id = id;
        this.name = name;
        this.projects = projects!=null ? projects : new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
