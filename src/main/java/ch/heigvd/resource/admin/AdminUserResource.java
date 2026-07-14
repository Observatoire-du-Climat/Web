package ch.heigvd.resource.admin;

import ch.heigvd.dto.UserDTO;
import ch.heigvd.service.MeasureService;
import ch.heigvd.service.UserService;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RolesAllowed("admin")
@Path("/admin/user")
public class AdminUserResource {

    @Inject
    Template user;

    @Inject
    UserService userService;

    @Inject
    MeasureService measureService;

    @Inject
    SecurityIdentity identity;

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/{id}")
    public TemplateInstance index(@PathParam("id") long id) {
        return user.data("user", userService.searchUserById(id))
                .data("measures", measureService.searchAllMeasuresByUserId(id))
                .data("adminName", identity.getPrincipal().getName());
    }

    @PUT
    @Path("/{id}")
    public Response validateUser(@PathParam("id") long id) {
        try {
            UserDTO userDTO = userService.validateUserById(id);
            System.out.println(userDTO);
            return Response.status(Response.Status.OK).entity(userDTO).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }
}
