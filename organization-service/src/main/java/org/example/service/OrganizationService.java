package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.example.entity.Organization;
import org.example.kafka.OrganizationKafkaProducer;
import org.example.repository.OrganizationRepository;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class OrganizationService {

    @Inject
    OrganizationRepository organizationRepository;

    @Inject
    OrganizationKafkaProducer organizationKafkaProducer;

    public List<Organization> getAllOrganizations() {
        return organizationRepository.listAll();
    }

    public Organization getOrganizationById(Long id) {
        return organizationRepository.findById(id);
    }

    @Transactional
    public Organization createOrganization(Organization organization) {
        organizationRepository.persist(organization);
        organizationKafkaProducer.sendOrganizationCreationEvent(organization.getId());
        return organization;
    }

    @Transactional
    public Organization updateOrganization(Long id, Organization organization) {
        Organization existingOrganization = organizationRepository.findById(id);
        if (existingOrganization != null) {
            existingOrganization.setName(organization.getName());
            existingOrganization.setAddress(organization.getAddress());
            existingOrganization.setEmployeeCount(organization.getEmployeeCount());
        }
        return existingOrganization;
    }

    @Transactional
    public boolean deleteOrganization(Long id) {
        return organizationRepository.deleteById(id);
    }
}
