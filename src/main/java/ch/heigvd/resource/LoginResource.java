package ch.heigvd.resource;

import ch.heigvd.dto.UserDTO;
import ch.heigvd.entity.User;
import ch.heigvd.service.UserService;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/api/login")
@Consumes("application/json")
@Produces("application/json")
public class LoginResource {

    @Inject
    EntityManager em;

    @Inject
    UserService userService;

    public record LoginRequest(String email, String password) {}

    @POST
    public Response login(LoginRequest request) {
        try {
            User user = userService.searchUserByEmail(request.email);
            if (BcryptUtil.matches(request.password, user.getPassword())) {
                throw new Exception("Unauthorized");
            }
            return Response.status(Response.Status.OK).entity(new UserDTO(user.getId(), user.getName(), user.getEmail())).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }


    }
}
