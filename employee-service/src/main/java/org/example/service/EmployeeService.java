package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.example.entity.Employee;
import org.example.kafka.EmployeeKafkaConsumer;
import org.example.kafka.EmployeeKafkaProducer;
import org.example.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class EmployeeService {

    @Inject
    EmployeeRepository employeeRepository;

    @Inject
    EmployeeKafkaProducer employeeKafkaProducer;

    public Optional<Employee> findByEmployeeId(Long employeeId) {
        return employeeRepository.findByIdOptional(employeeId);
    }

    public List<Employee> findAll() {
        return employeeRepository.listAll();
    }

    @Transactional
    public Employee createEmployee(Employee employee) {
        employeeRepository.persist(employee);
        employeeKafkaProducer.publishEmployeeCreatedEvent(employee.getId());  // Publie l'événement de création
        return employee;
    }

    @Transactional
    public Employee updateEmployee(Long id, Employee employeeData) {
        Employee employee = employeeRepository.findById(id);
        if (employee != null) {
            employee.setName(employeeData.getName());
            employee.setAge(employeeData.getAge());
            employee.setPosition(employeeData.getPosition());
            employee.setDepartmentId(employeeData.getDepartmentId());
            employee.setOrganizationId(employeeData.getOrganizationId());
            employeeRepository.persist(employee);
            employeeKafkaProducer.publishEmployeeUpdatedEvent(employee.getId());  // Publie l'événement de mise à jour
        }
        return employee;
    }

    @Transactional
    public boolean deleteEmployee(Long id) {
        boolean deleted = employeeRepository.deleteById(id);
        if (deleted) {
            employeeKafkaProducer.publishEmployeeDeletedEvent(id);  // Publie l'événement de suppression
        }
        return deleted;
    }



}
