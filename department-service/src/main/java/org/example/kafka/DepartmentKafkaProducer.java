package org.example.kafka;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class DepartmentKafkaProducer {

    @Inject
    @Channel("department-events")
    Emitter<String> departmentEmitter;

    public void sendDepartmentCreationEvent(Long departmentId) {
        String message = "Department " + departmentId + " created";
        departmentEmitter.send(message);
    }
}
