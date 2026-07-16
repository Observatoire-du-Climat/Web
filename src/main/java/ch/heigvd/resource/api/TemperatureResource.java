package ch.heigvd.resource.api;

import ch.heigvd.dto.TemperatureMeasureDTO;
import ch.heigvd.service.PictureService;
import ch.heigvd.service.TemperatureService;
import io.quarkus.security.UnauthorizedException;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.time.LocalDate;

/**
 * Class responsible for the REST resource exposing Temperature-related endpoints.
 */
@Path("/api/measures/temperature")
@Produces(MediaType.APPLICATION_JSON)
public class TemperatureResource {

    @Inject
    TemperatureService temperatureService;

    @Inject
    PictureService pictureService;

    /**
     * Request payload used to create or update a Temperature measure.
     * @param userId the id of the user who did the measure.
     * @param date the date of the measure.
     * @param location the location where the measure was taken.
     * @param degree the measured temperature in degrees Celsius.
     */
    public record TemperatureRequest(Long userId, LocalDate date, String location, Integer degree) {}

    /**
     * Create a new temperature measure.
     * @param request the measure data.
     * @param picture the optional picture associated with the measure.
     * @return the appropriate HTTP Response, containing the measure if successful.
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response createTemperatureMeasure(@RestForm("request") @PartType(MediaType.APPLICATION_JSON) TemperatureRequest request,
                                                        @RestForm("picture") FileUpload picture) {
        try {
            var temperatureMeasureDTO = temperatureService.addTemperature(request.userId(), request.date(), request.location(), request.degree());
            if (picture != null) {
                pictureService.addPicture(picture, temperatureMeasureDTO.id());
            }
            return Response.status(Response.Status.CREATED).entity(temperatureMeasureDTO).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (ForbiddenException e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    /**
     * Update an existing Temperature measure.
     * @param id the id of the measure to update.
     * @param request the (new) measure data.
     * @return the appropriate HTTP Response, containing the measure if successful.
     */
    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTemperatureMeasure(@PathParam("id") Long id, TemperatureRequest request) {
        try {
            TemperatureMeasureDTO temperatureMeasureDTO = temperatureService.modifyTemperatureById(id, request.date(), request.location(), request.degree());
            return Response.status(Response.Status.OK).entity(temperatureMeasureDTO).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
