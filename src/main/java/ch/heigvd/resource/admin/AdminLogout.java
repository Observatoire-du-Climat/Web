package ch.heigvd.resource.admin;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("/admin/logout")
public class AdminLogout {

    @GET
    public Response logout() {
        return Response.seeOther(URI.create("/admin/login")).build();
    }
}
