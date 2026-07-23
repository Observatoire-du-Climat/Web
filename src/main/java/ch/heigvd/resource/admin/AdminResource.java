package ch.heigvd.resource.admin;

import ch.heigvd.service.MeasureService;
import ch.heigvd.service.PictureService;
import ch.heigvd.service.UserService;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
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
    PictureService pictureService;

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
    public TemplateInstance index(@QueryParam("userSearch") String userSearch,
                                  @QueryParam("order") String order,
                                  @QueryParam("asc") @DefaultValue("true") boolean asc) {
        return admin.data("users", userService.getAllUser(userSearch))
                .data("userSearch", userSearch)
                .data("measures", measureService.getAllMeasures(order, asc))
                .data("measureCount", measureService.getAllMeasures("", asc).size())
                .data("pictureCount", pictureService.getPictureCount())
                .data("order", order)
                .data("asc", asc)
                .data("adminName", identity.getPrincipal().getName());
    }
}
