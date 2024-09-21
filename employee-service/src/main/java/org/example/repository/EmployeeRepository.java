package org.example.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.example.entity.Employee;


import java.util.Optional;

@ApplicationScoped
public class EmployeeRepository implements PanacheRepository<Employee> {

}
