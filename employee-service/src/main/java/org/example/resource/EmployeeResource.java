package org.example.resource;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.entity.Employee;
import org.example.service.EmployeeService;

import java.util.List;

@Path("/employee")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeResource {

    @Inject
    EmployeeService employeeService;

    @GET
    public List<Employee> getAllEmployees() {
        return employeeService.findAll();
    }

    @GET
    @Path("/{employeeId}")
    public Response getEmployee(@PathParam("employeeId") Long employeeId) {
        return employeeService.findByEmployeeId(employeeId)
                .map(employee -> Response.ok(employee).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    public Response createEmployee(Employee employee) {
        Employee created = employeeService.createEmployee(employee);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateEmployee(@PathParam("id") Long id, Employee employee) {
        Employee updated = employeeService.updateEmployee(id, employee);
        if (updated != null) {
            return Response.ok(updated).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteEmployee(@PathParam("id") Long id) {
        boolean deleted = employeeService.deleteEmployee(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


}

