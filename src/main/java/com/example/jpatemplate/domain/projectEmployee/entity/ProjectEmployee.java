package com.example.jpatemplate.domain.projectEmployee.entity;

import com.example.jpatemplate.domain.employee.entity.Employee;
import com.example.jpatemplate.domain.project.entity.Project;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProjectEmployee {

    @Id
    @ManyToOne
    private Project project;

    @Id
    @ManyToOne
    private Employee employee;

    @Builder
    public ProjectEmployee(Project project, Employee employee) {
        this.project = project;
        this.employee = employee;
    }

    // need to override equals because in method Project.addEmployee(), we initialize new instance of ProjectEmployee -> cannot remove exact ProjectEmployee without equals method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectEmployee that = (ProjectEmployee) o;
        return project.equals(that.project) && employee.equals(that.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(project, employee);
    }
}
