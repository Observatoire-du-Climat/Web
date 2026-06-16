package ch.heigvd.resource;

import ch.heigvd.dto.BirdMigrationMeasureDTO;
import ch.heigvd.service.BirdMigrationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;

@Path("/api/measures/bird-migration")
@Consumes("application/json")
@Produces("application/json")
public class BirdMigrationResource {

    @Inject
    BirdMigrationService birdMigrationService;
    /*
    @Inject
    MeasureService measureService;
    */

    public record BirdMigrationRequest(Long userId, LocalDate date, String location, String specie, String event) {}
    /*
    @Path("/{id}")
    @GET
    public Response getBirdMigrationMeasure(@PathParam("id") Long id) {
        try {
            var birdMigrationMeasureDTO = measureService.searchMeasureById(id);
            return Response.status(Response.Status.OK).entity(birdMigrationMeasureDTO).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
     */

    @POST
    public Response createBirdMigrationMeasure(BirdMigrationRequest request) {
        try {
            var birdMigrationMeasureDTO = birdMigrationService.addBirdMigration(request.userId(), request.date(), request.location(), request.specie, request.event);
            return Response.status(Response.Status.CREATED).entity(birdMigrationMeasureDTO).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Path("/{id}")
    @PUT
    public Response updateBirdMigrationMeasure(@PathParam("id") Long id, BirdMigrationRequest request) {
        try {
            BirdMigrationMeasureDTO birdMigrationMeasureDTO = birdMigrationService.modifyBirdMigrationById(id, request.date(), request.location(), request.specie, request.event);
            return Response.status(Response.Status.OK).entity(birdMigrationMeasureDTO).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
