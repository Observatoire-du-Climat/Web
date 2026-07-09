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

@Path("/admin/login")
public class AdminLoginResource {

    @Inject
    Template login;

    @Inject
    UserService userService;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance loginPage(@QueryParam("error") String error) {
        return login.data("error", error != null);
    }

    /*
    @POST
    public Response login(@FormParam("email") String email, @FormParam("password") String password) {
        User user = userService.searchUserByEmail(email);
        if (!BcryptUtil.matches(password, user.getPassword())) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        NewCookie cookie = new NewCookie.Builder("admin-session")
                .value(UUID.randomUUID().toString())
                .path("/")
                //.domain("domain.com")
                .sameSite(NewCookie.SameSite.LAX)
                .maxAge(60*2)
                .build();

        return Response.seeOther(URI.create("/admin"))
                .cookie(cookie)
                .build();
    }
     */
}
