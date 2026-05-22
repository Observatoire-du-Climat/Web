package ch.heigvd.resource;

import ch.heigvd.dto.UserDTO;
import ch.heigvd.service.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/api/users")
@Consumes("application/json")
@Produces("application/json")
public class UserResource {

    @Inject
    UserService userService;

    public record UserRequest(String name, String email, String password) {}

    @POST
    public Response createUser(UserRequest userRequest) {
        try {
            userService.addUser(userRequest.name, userRequest.email, userRequest.password);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Path("/{id}")
    @GET
    public Response getUser(@PathParam("id") Long id) {
        try {
            UserDTO userDTO = userService.searchUserById(id);
            return Response.status(Response.Status.OK).entity(userDTO).build();
        } catch(Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Path("/{id}")
    @PUT
    public Response updateUser(@PathParam("id") Long id, UserRequest userRequest) {
        try {
            UserDTO userDTO = userService.modifyUserById(id,userRequest.name, userRequest.email, userRequest.password).orElseThrow();
            return Response.status(Response.Status.OK).entity(userDTO).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
