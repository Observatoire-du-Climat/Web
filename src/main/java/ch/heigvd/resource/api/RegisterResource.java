package ch.heigvd.resource.api;

import ch.heigvd.dto.UserDTO;
import ch.heigvd.service.UserService;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/api/register")
@Consumes("application/json")
@Produces("application/json")
public class RegisterResource {

    @Inject
    EntityManager em;

    @Inject
    UserService userService;

    public record RegisterRequest(String name, String email, String password) {}

    @POST
    public Response register(RegisterRequest request) {
        try {
            UserDTO userDTO = userService.addUser(request.name(), request.email(), request.password());
            return Response.status(Response.Status.CREATED).entity(userDTO).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

}
