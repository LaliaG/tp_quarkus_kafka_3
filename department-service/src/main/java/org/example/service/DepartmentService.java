package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.example.entity.Department;
import org.example.kafka.DepartmentKafkaProducer;
import org.example.repository.DepartmentRepository;

import java.util.List;

@ApplicationScoped
public class DepartmentService {

    @Inject
    DepartmentRepository departmentRepository;

    @Inject
    DepartmentKafkaProducer departmentKafkaProducer;

    public List<Department> getAllDepartments() {
        return departmentRepository.listAll();
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }

    @Transactional
    public Department createDepartment(Department department) {
        departmentRepository.persist(department);
        departmentKafkaProducer.sendDepartmentCreationEvent(department.getId());
        return department;
    }

    @Transactional
    public Department updateDepartment(Long id, Department department) {
        Department existingDepartment = departmentRepository.findById(id);
        if (existingDepartment != null) {
            existingDepartment.setName(department.getName());
            existingDepartment.setOrganizationId(department.getOrganizationId());
        }
        return existingDepartment;
    }

    @Transactional
    public boolean deleteDepartment(Long id) {
        return departmentRepository.deleteById(id);
    }
}
