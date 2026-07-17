package ch.heigvd.resource.admin;

import ch.heigvd.service.MeasureService;
import ch.heigvd.service.UserService;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

/**
 * Class responsible for the administration web page.
 */
@RolesAllowed("admin")
@Path("/admin")
public class AdminResource {

    @Inject
    Template admin;

    @Inject
    UserService userService;
    @Inject
    MeasureService measureService;

    @Inject
    SecurityIdentity identity;

    @Inject
    public AdminResource(Template admin) {
        this.admin = admin;
    }

    /**
     * Display the main administration page.
     * @param userSearch the optional userSearch query.
     * @param order the optional measure sorting query.
     * @return the rendered administration page.
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance index(@QueryParam("userSearch") String userSearch, @QueryParam("order") String order) {
        return admin.data("users", userService.getAllUser(userSearch))
                .data("userSearch", userSearch)
                .data("measures", measureService.getAllMeasures(order))
                .data("order", order)
                .data("adminName", identity.getPrincipal().getName());
    }
}
