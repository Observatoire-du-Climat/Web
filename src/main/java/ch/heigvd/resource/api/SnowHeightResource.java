package ch.heigvd.resource.api;

import ch.heigvd.dto.SnowHeightMeasureDTO;
import ch.heigvd.service.PictureService;
import ch.heigvd.service.SnowHeightService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.time.LocalDate;

/**
 * Class responsible for the REST resource exposing SnowHeight-related endpoints.
 */
@Path("/api/measures/snow-height")
@Produces(MediaType.APPLICATION_JSON)
public class SnowHeightResource {

    @Inject
    SnowHeightService snowHeightService;

    @Inject
    PictureService pictureService;

    /**
     * Request payload used to create or update a SnowHeight measure.
     * @param userId the id of the user who did the measure.
     * @param date the date of the measure.
     * @param location the location where the measure was taken.
     * @param height the measured height of the snow.
     * @param weather the weather condition of the measure.
     * @param precipitation the measured precipitation of the day.
     */
    public record SnowHeightRequest(Long userId, LocalDate date, String location, Integer height, String weather, Integer precipitation) {}

    /**
     * Create a new SnowHeight measure.
     * @param request the measure data.
     * @param picture the optional picture associated with the measure.
     * @return the appropriate HTTP Response, containing the measure if successful.
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response createSnowHeightMeasure(@RestForm("request") @PartType(MediaType.APPLICATION_JSON) SnowHeightRequest request,
                                            @RestForm("picture")FileUpload picture) {
        try {
            var snowHeightMeasureDTO = snowHeightService.addSnowHeight(request.userId(), request.date(), request.location(), request.height(), request.weather(), request.precipitation());
            if (picture != null) {
                pictureService.addPicture(picture, snowHeightMeasureDTO.id());
            }
            return Response.status(Response.Status.CREATED).entity(snowHeightMeasureDTO).build();
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
     * @param id the id of the measure to update.
     * @param request the (new) data of the measure.
     * @return the appropriate HTTP Response, containing the measure if successful.
     */
    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateSnowHeightMeasure(@PathParam("id") Long id, SnowHeightRequest request) {
        try {
            SnowHeightMeasureDTO snowHeightMeasureDTO = snowHeightService.modifySnowHeightById(id, request.date(), request.location(), request.height(), request.weather(), request.precipitation());
            return Response.status(Response.Status.OK).entity(snowHeightMeasureDTO).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
