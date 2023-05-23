package com.example.jpatemplate.employee;

import com.example.jpatemplate.project.Project;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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

    @ManyToMany(mappedBy = "employees")
    private List<Project> projects;

    @Builder
    public Employee(Long id, String name, List<Project> projects) {
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
