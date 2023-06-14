package com.example.jpatemplate.domain.employee.entity;

import com.example.jpatemplate.domain.projectEmployee.entity.ProjectEmployee;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
