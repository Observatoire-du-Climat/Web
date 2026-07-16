package ch.heigvd.resource.admin;

import ch.heigvd.entity.User;
import ch.heigvd.service.UserService;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;

import java.awt.*;
import java.net.URI;
import java.util.UUID;

/**
 * Class responsible for the administration login web page.
 */
@Path("/admin/login")
public class AdminLoginResource {

    @Inject
    Template login;

    @Inject
    UserService userService;

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
