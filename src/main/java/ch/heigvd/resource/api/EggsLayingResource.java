package ch.heigvd.resource.api;

import ch.heigvd.dto.EggsLayingMeasureDTO;
import ch.heigvd.service.EggsLayingService;
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
@Path("/api/measures/eggs-laying")
@Produces(MediaType.APPLICATION_JSON)
public class EggsLayingResource {

    @Inject
    EggsLayingService eggsLayingService;

    @Inject
    PictureService pictureService;

    /**
     * Request payload used to create or update a BirdMigration measure.
     * @param userId the id of the user who did the measure.
     * @param date the date of the measure.
     * @param location the location where the measure was taken.
     * @param number the measured number of eggs.
     */
    public record EggsLayingRequest(Long userId, LocalDate date, String location, Integer number) {}

    /**
     * Create an EggsLaying measure.
     * @param request the measure data.
     * @param picture the optional picture associated with the measure.
     * @return the appropriate HTTP Response, containing the measure if successful.
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response createEggsLayingMeasure(@RestForm("request") @PartType(MediaType.APPLICATION_JSON) EggsLayingRequest request,
                                            @RestForm("picture") FileUpload picture) {
        try {
            var eggsLayingMeasureDTO = eggsLayingService.addEggsLaying(request.userId, request.date, request.location, request.number);
            if (picture != null) {
                pictureService.addPicture(picture, eggsLayingMeasureDTO.id());
            }
            return Response.status(Response.Status.CREATED).entity(eggsLayingMeasureDTO).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (ForbiddenException e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    /**
     * Update an existing EggsLaying measure.
     * @param id the id of the measure.
     * @param request the (new) measure data.
     * @return the appropriate HTTP Response, containing the measure if successful.
     */
    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEggsLayingMeasure(@PathParam("id") Long id, EggsLayingRequest request) {
        try {
            EggsLayingMeasureDTO eggsLayingMeasureDTO = eggsLayingService.modifyEggsLayingById(id, request.date, request.location, request.number);
            return Response.status(Response.Status.OK).entity(eggsLayingMeasureDTO).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
