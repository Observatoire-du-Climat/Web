package ch.heigvd.resource.admin;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.awt.*;


/**
 * Class responsible for the administration login web page.
 */
@Path("/admin/login")
public class AdminLoginResource {

    @Inject
    Template login;

    /**
     * Log in as an administrator
     * @param error if the login was an error
     * @return a redirection to the main administration page if the login is successful.
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance loginPage(@QueryParam("error") String error) {
        return login.data("error", error != null);
    }
}
