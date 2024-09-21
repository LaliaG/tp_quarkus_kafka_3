package org.example.kafka;

import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.example.entity.Employee;
import org.example.repository.EmployeeRepository;

@ApplicationScoped
public class EmployeeKafkaConsumer {

    @Inject
    EmployeeRepository employeeRepository;

    @Incoming("employee-events")
    @Blocking
    @Transactional
    public void consumeEmployeeEvent(String message) {
        if (message.contains("created")) {
            Long employeeId = parseEmployeeIdFromMessage(message);
            // Handle employee created logic (optional)
            System.out.println("Employee created: " + employeeId);
        } else if (message.contains("updated")) {
            Long employeeId = parseEmployeeIdFromMessage(message);
            // Handle employee updated logic (optional)
            System.out.println("Employee updated: " + employeeId);
        } else if (message.contains("deleted")) {
            Long employeeId = parseEmployeeIdFromMessage(message);
            // Handle employee deleted logic
            Employee employee = employeeRepository.findById(employeeId);
            if (employee != null) {
                employeeRepository.deleteById(employeeId);
            }
            System.out.println("Employee deleted: " + employeeId);
        }
    }

    private Long parseEmployeeIdFromMessage(String message) {
        String[] parts = message.split(" ");
        return Long.parseLong(parts[1]);
    }

    /*@Inject
    EmployeeRepository employeeRepository;

    @Incoming("employee-events")
    @Blocking
    @Transactional
    public void handleEmployeeEvent(String message) {
        Long employeeId = parseEmployeeIdFromMessage(message);
        String eventType = parseEventTypeFromMessage(message);
        Employee employee = employeeRepository.findById(employeeId);
        if (employee != null) {
            // Logic to handle event (e.g., status update)
            System.out.println("Processing event: " + eventType + " for employee: " + employeeId);
        }
    }

    private Long parseEmployeeIdFromMessage(String message) {
        String[] parts = message.split(" ");
        return Long.parseLong(parts[1]);
    }

    private String parseEventTypeFromMessage(String message) {
        String[] parts = message.split(": ");
        return parts[1];
    }*/



}
