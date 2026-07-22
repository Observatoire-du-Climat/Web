package ch.heigvd.resource.admin;

import ch.heigvd.service.MeasureService;
import ch.heigvd.service.UserService;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

/**
 * Class responsible for a sub administration web page, displaying a user details.
 */
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

    /**
     * Display a user details page.
     * @param id the id of the user.
     * @return the renderer user details page.
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/{id}")
    public TemplateInstance index(@PathParam("id") long id) {
        return user.data("user", userService.searchUserById(id))
                .data("measures", measureService.searchAllMeasuresByUserId(id))
                .data("adminName", identity.getPrincipal().getName());
    }
}
