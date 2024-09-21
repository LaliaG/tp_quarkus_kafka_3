package org.example.resource;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.entity.Organization;
import org.example.service.OrganizationService;

import java.util.List;

@Path("/organization")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrganizationResource {

    @Inject
    OrganizationService organizationService;

    @GET
    public List<Organization> getAllOrganizations() {
        return organizationService.getAllOrganizations();
    }

    @GET
    @Path("/{id}")
    public Response getOrganizationById(@PathParam("id") Long id) {
        Organization organization = organizationService.getOrganizationById(id);
        if (organization != null) {
            return Response.ok(organization).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response createOrganization(Organization organization) {
        Organization created = organizationService.createOrganization(organization);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateOrganization(@PathParam("id") Long id, Organization organization) {
        Organization updated = organizationService.updateOrganization(id, organization);
        if (updated != null) {
            return Response.ok(updated).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteOrganization(@PathParam("id") Long id) {
        boolean deleted = organizationService.deleteOrganization(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}

