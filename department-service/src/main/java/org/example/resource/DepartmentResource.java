package org.example.resource;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.entity.Department;
import org.example.service.DepartmentService;

import java.util.List;

@Path("/department")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DepartmentResource {

    @Inject
    DepartmentService departmentService;

    @GET
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @GET
    @Path("/{id}")
    public Response getDepartmentById(@PathParam("id") Long id) {
        Department department = departmentService.getDepartmentById(id);
        if (department != null) {
            return Response.ok(department).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response createDepartment(Department department) {
        Department created = departmentService.createDepartment(department);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateDepartment(@PathParam("id") Long id, Department department) {
        Department updated = departmentService.updateDepartment(id, department);
        if (updated != null) {
            return Response.ok(updated).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteDepartment(@PathParam("id") Long id) {
        boolean deleted = departmentService.deleteDepartment(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


}

