package com.example.jpatemplate.entity.relationship;

import com.example.jpatemplate.common.jpa.annotation.RepositoryTest;
import com.example.jpatemplate.domain.employee.entity.Employee;
import com.example.jpatemplate.domain.project.entity.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@RepositoryTest
public class BiDirectionalManyToManyTest {

    @PersistenceContext
    EntityManager em;

    @Test
    void persist_test(){
        var project1 = Project.builder().id(1L).build();
        var project2 = Project.builder().id(2L).build();
        var project3 = Project.builder().id(3L).build();

        var employee1 = Employee.builder().id(1L).name("Sam").build();
        var employee2 = Employee.builder().id(2L).name("Helen").build();
        var employee3 = Employee.builder().id(3L).name("David").build();

        // persist each projects, employees first -> set relationship
        // vice versa does not work because if set relationship first then 1. insert project 2. insert relationship -> error
        em.persist(project1);
        em.persist(project2);
        em.persist(project3);


        em.persist(employee1);
        em.persist(employee2);
        em.persist(employee3);

        project1.addEmployee(employee1);
        project1.addEmployee(employee2);

        project2.addEmployee(employee2);
        project2.addEmployee(employee3);

        em.flush();
        em.clear();

        var project1FromContext = em.find(Project.class, 1L);
        var project2FromContext = em.find(Project.class, 2L);
        var employee1FromContext = em.find(Employee.class, 1L);
        var employee2FromContext = em.find(Employee.class, 2L);
        var employee3FromContext = em.find(Employee.class, 3L);
        assertEquals(project1.getEmployees().size(), project1FromContext.getEmployees().size());
        assertEquals(employee1.getId(), project1FromContext.getEmployees().get(0).getEmployee().getId());
        assertEquals(project2.getEmployees().size(), project2FromContext.getEmployees().size());
        assertEquals(employee1.getProjects().size(),employee1FromContext.getProjects().size());
        assertEquals(employee2.getProjects().size(),employee2FromContext.getProjects().size());
        assertEquals(employee3.getProjects().size(),employee3FromContext.getProjects().size());
    }

//    @Test
//    void when_set_relationship_on_inverse_side_then_relationship_does_not_work_test(){
//        var project1 = Project.builder().id(1L).build();
//        var project2 = Project.builder().id(2L).build();
//        var project3 = Project.builder().id(3L).build();
//
//        var employee1 = Employee.builder().id(1L).name("Sam").build();
//        var employee2 = Employee.builder().id(2L).name("Helen").build();
//        var employee3 = Employee.builder().id(3L).name("David").build();
//
//        employee1.getProjects().add(project1);
//        employee2.getProjects().add(project1);
//        employee2.getProjects().add(project2);
//        employee3.getProjects().add(project2);
//
//        em.persist(project1);
//        em.persist(project2);
//        em.persist(project3);
//
//
//        em.persist(employee1);
//        em.persist(employee2);
//        em.persist(employee3);
//
//        em.flush();
//        em.clear();
//
//        var project1FromContext = em.find(Project.class, 1L);
//        var project2FromContext = em.find(Project.class, 2L);
//        var employee1FromContext = em.find(Employee.class, 1L);
//        var employee2FromContext = em.find(Employee.class, 2L);
//        var employee3FromContext = em.find(Employee.class, 3L);
//        assertEquals(0, project1FromContext.getEmployees().size());
//        assertEquals(0, project2FromContext.getEmployees().size());
//        assertEquals(0,employee1FromContext.getProjects().size());
//        assertEquals(0,employee2FromContext.getProjects().size());
//        assertEquals(0,employee3FromContext.getProjects().size());
//    }

    @Test
    void remove_relationship_test(){
        var project1 = Project.builder().id(1L).build();

        var employee1 = Employee.builder().id(1L).name("Sam").build();
        var employee2 = Employee.builder().id(2L).name("Helen").build();
        var employee3 = Employee.builder().id(3L).name("David").build();

        em.persist(project1);


        em.persist(employee1);
        em.persist(employee2);
        em.persist(employee3);

        project1.addEmployee(employee1);
        project1.addEmployee(employee2);
        project1.addEmployee(employee3);




        em.flush();

        project1.removeEmployee(employee1);

        em.persist(project1);

        em.flush();
        em.clear();

        var project1FromContext = em.find(Project.class, 1L);
        assertEquals(project1.getEmployees().size(), project1FromContext.getEmployees().size());
        assertEquals(employee2.getId(), project1FromContext.getEmployees().get(0).getEmployee().getId());
    }
}
