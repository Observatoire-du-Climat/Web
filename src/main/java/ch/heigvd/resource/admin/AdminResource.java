package ch.heigvd.resource.admin;

import ch.heigvd.service.MeasureService;
import ch.heigvd.service.UserService;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;

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
    public AdminResource(Template admin) {
        this.admin = admin;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance index() {
        return admin.data("users", userService.getAllUser())
                .data("measures", measureService.getAllMeasures());
    }
}
