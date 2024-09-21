package org.example.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.example.entity.Department;

import java.util.Optional;

@ApplicationScoped
public class DepartmentRepository implements PanacheRepository<Department> {

    public Optional<Department> findByProductId(Long productId) {
        return find("productId", productId).firstResultOptional();
    }
}
