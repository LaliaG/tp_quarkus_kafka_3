package org.example.kafka;

import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.example.entity.Department;
import org.example.repository.DepartmentRepository;

@ApplicationScoped
public class DepartmentKafkaConsumer {

    @Inject
    DepartmentRepository departmentRepository;

    @Incoming("department-events")
    @Blocking
    @Transactional
    public void handleDepartmentEvent(String message) {
        Long departmentId = parseDepartmentIdFromMessage(message);
        String eventType = parseEventTypeFromMessage(message);
        Department department = departmentRepository.findById(departmentId);
        if (department != null) {
            // Logic to handle event (e.g., status update)
            System.out.println("Processing event: " + eventType + " for department: " + departmentId);
        }
    }

    private Long parseDepartmentIdFromMessage(String message) {
        String[] parts = message.split(" ");
        return Long.parseLong(parts[1]);
    }

    private String parseEventTypeFromMessage(String message) {
        String[] parts = message.split(": ");
        return parts[1];
    }

    @Incoming("department-events")
    public void onDepartmentEvent(String message) {
        System.out.println("Received department event: " + message);
    }
}
