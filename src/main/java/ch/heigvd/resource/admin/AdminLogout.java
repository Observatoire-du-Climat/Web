package ch.heigvd.resource.admin;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.net.URI;

/**
 * Class responsible for the logout.
 */
@Path("/admin/logout")
public class AdminLogout {

    /**
     * Log out from the admin account
     * @return a redirection to the login page.
     */
    @GET
    public Response logout() {
        return Response.seeOther(URI.create("/admin/login")).build();
    }
}
