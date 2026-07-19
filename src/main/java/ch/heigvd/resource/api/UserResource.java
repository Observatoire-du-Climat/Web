package ch.heigvd.resource.api;

import ch.heigvd.dto.UserDTO;
import ch.heigvd.service.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Class responsible for the REST resource exposing user-related endpoints.
 */
@Path("/api/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;

    /**
     * Request payload used to update a User
     * @param name the (new) name of the user
     * @param email the (new) email of the user
     * @param password the (new) email of the user
     */
    public record UserRequest(String name, String email, String password) {}

    /**
     * Get a User by its id.
     * @param id the id of the user
     * @return The appropriate HTTP Response, containing the user if successful.
     */
    @Path("/{id}")
    @GET
    public Response getUser(@PathParam("id") Long id) {
        try {
            UserDTO userDTO = userService.searchUserById(id);
            return Response.status(Response.Status.OK).entity(userDTO).build();
        } catch(NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    /**
     * Update an existing User.
     * @param id the id of the user
     * @param userRequest the updated user information.
     * @return the appropriate HTTP Response, containing the user if successful.
     */
    @Path("/{id}")
    @PUT
    public Response updateUser(@PathParam("id") Long id, UserRequest userRequest) {
        try {
            UserDTO userDTO = userService.modifyUserById(id,userRequest.name(), userRequest.email(), userRequest.password());
            return Response.status(Response.Status.OK).entity(userDTO).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
