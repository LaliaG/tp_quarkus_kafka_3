package org.example.kafka;

import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.example.entity.Organization;
import org.example.repository.OrganizationRepository;

@ApplicationScoped
public class OrganizationKafkaConsumer {

    @Inject
    OrganizationRepository organizationRepository;

    @Incoming("organization-events")
    @Blocking
    @Transactional
    public void handleOrganizationEvent(String message) {
        Long organizationId = parseOrganizationIdFromMessage(message);
        String eventType = parseEventTypeFromMessage(message);
        Organization organization = organizationRepository.findById(organizationId);
        if (organization != null) {
            // Logic to handle event (e.g., status update)
            System.out.println("Processing event: " + eventType + " for organization: " + organizationId);
        }
    }

    private Long parseOrganizationIdFromMessage(String message) {
        String[] parts = message.split(" ");
        return Long.parseLong(parts[1]);
    }

    private String parseEventTypeFromMessage(String message) {
        String[] parts = message.split(": ");
        return parts[1];
    }

    @Incoming("organization-events")
    public void onOrganizationEvent(String message) {
        System.out.println("Received organization event: " + message);
    }
}
