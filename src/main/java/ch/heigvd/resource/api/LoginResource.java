package ch.heigvd.resource.api;

import ch.heigvd.dto.UserDTO;
import ch.heigvd.entity.User;
import ch.heigvd.service.UserService;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

/**
 * Class responsible for the REST resource exposing the login endpoint.
 */
@Path("/api/login")
@Consumes("application/json")
@Produces("application/json")
public class LoginResource {

    @Inject
    UserService userService;

    /**
     * Request payload used to log in as a User.
     * @param email the email of the user.
     * @param password the password of the user.
     */
    public record LoginRequest(String email, String password) {}

    /**
     * Log in as a User
     * @param request the user data.
     * @return the appropriate HTTP Response, containing the user if successful.
     */
    @POST
    public Response login(LoginRequest request) {
        try {
            User user = userService.searchUserByEmail(request.email());
            if (!BcryptUtil.matches(request.password(), user.getPassword())) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            return Response.status(Response.Status.OK).entity(new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getValid())).build();

        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }


    }
}
