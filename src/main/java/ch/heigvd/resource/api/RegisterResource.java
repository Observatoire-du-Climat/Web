package ch.heigvd.resource.api;

import ch.heigvd.dto.UserDTO;
import ch.heigvd.service.UserService;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Class responsible for the REST resource exposing the register endpoint.
 */
@Path("/api/register")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RegisterResource {

    @Inject
    EntityManager em;

    @Inject
    UserService userService;

    /**
     * Request payload used to create a new User.
     * @param name the name of the user
     * @param email the email of the user
     * @param password the password of the user
     */
    public record RegisterRequest(String name, String email, String password) {}

    /**
     * Create a new User.
     * @param request the user data.
     * @return the appropriate HTTP Response, containing the user if successful.
     */
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
