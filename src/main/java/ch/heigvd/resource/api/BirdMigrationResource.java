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

/**
 * Class responsible for the REST resource exposing BirdMigration-related endpoints.
 */
@Path("/api/measures/bird-migration")
@Produces(MediaType.APPLICATION_JSON)
public class BirdMigrationResource {

    @Inject
    BirdMigrationService birdMigrationService;

    @Inject
    PictureService pictureService;

    /**
     * Request payload used to create or update a BirdMigration measure.
     * @param userId the id of the user who did the measure.
     * @param date the date of the measure.
     * @param location the location where the measure was taken.
     * @param specie the observed specie.
     * @param event the observed event.
     */
    public record BirdMigrationRequest(Long userId, LocalDate date, String location, String specie, String event) {}

    /**
     * Create a BirdMigration measure.
     * @param request the measure data.
     * @param picture the optional picture associated with the measure.
     * @return the appropriate HTTP Response, containing the measure if successful.
     */
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
        } catch (ForbiddenException e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    /**
     * Update an existing SnowHeight measure.
     * @param id the id of the measure.
     * @param request the (new) measure data.
     * @return the appropriate HTTP Response, containing the measure if successful.
     */
    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
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
