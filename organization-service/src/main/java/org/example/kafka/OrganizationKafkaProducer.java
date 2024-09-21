package org.example.kafka;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class OrganizationKafkaProducer {

    @Inject
    @Channel("organization-events")
    Emitter<String> organizationEmitter;

    public void sendOrganizationCreationEvent(Long organizationId) {
        String message = "Organization " + organizationId + " created";
        organizationEmitter.send(message);
    }

}
