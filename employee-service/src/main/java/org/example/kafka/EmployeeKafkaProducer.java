package org.example.kafka;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class EmployeeKafkaProducer {
    @Inject
    @Channel("employee-events")
    Emitter<String> employeeEventsEmitter;

    public void publishEmployeeCreatedEvent(Long employeeId) {
        String message = "Employee " + employeeId + " has been created.";
        employeeEventsEmitter.send(message);
    }

    public void publishEmployeeUpdatedEvent(Long employeeId) {
        String message = "Employee " + employeeId + " has been updated.";
        employeeEventsEmitter.send(message);
    }

    public void publishEmployeeDeletedEvent(Long employeeId) {
        String message = "Employee " + employeeId + " has been deleted.";
        employeeEventsEmitter.send(message);
    }

    /* @Inject
    @Channel("employee-events")
    Emitter<String> employeeEmitter;

    public void publishEmployeeEvent(Long employeeId, String eventType) {
        String message = "Employee " + employeeId + " event: " + eventType;
        employeeEmitter.send(message);
    }*/
}
