package org.example.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.example.entity.Organization;

import java.util.Optional;

@ApplicationScoped
public class OrganizationRepository implements PanacheRepository<Organization> {

}
