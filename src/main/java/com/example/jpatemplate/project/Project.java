package com.example.jpatemplate.project;

import com.example.jpatemplate.employee.Employee;
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
public class Project {

    @Id
    private Long id;

    @ManyToMany
    private List<Employee> employees;

    @Builder
    public Project(Long id, List<Employee> employees) {
        this.id = id;
        this.employees = employees!=null ? employees : new ArrayList<>();
    }

    public void addEmployee(Employee employee){
        this.employees.add(employee);
        employee.getProjects().add(this);
    }

    public void removeEmployee(Employee employee){
        this.employees.remove(employee);
        employee.getProjects().remove(this);
    }


    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", employees=" + employees +
                '}';
    }
}
