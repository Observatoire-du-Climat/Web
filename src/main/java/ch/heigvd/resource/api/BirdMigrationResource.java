package ch.heigvd.resource.api;

import ch.heigvd.dto.BirdMigrationMeasureDTO;
import ch.heigvd.service.BirdMigrationService;
import ch.heigvd.service.PictureService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.time.LocalDate;

@Path("/api/measures/bird-migration")
@Consumes("application/json")
@Produces("application/json")
public class BirdMigrationResource {

    @Inject
    BirdMigrationService birdMigrationService;

    @Inject
    PictureService pictureService;

    public record BirdMigrationRequest(Long userId, LocalDate date, String location, String specie, String event) {}

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response createBirdMigrationMeasure(@RestForm("request") @PartType(MediaType.APPLICATION_JSON) BirdMigrationRequest request,
                                               @RestForm("picture")FileUpload picture) {
        try {
            var birdMigrationMeasureDTO = birdMigrationService.addBirdMigration(request.userId(), request.date(), request.location(), request.specie, request.event);
            if (picture != null) {
                pictureService.addPicture(picture, birdMigrationMeasureDTO.id());
            }
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
