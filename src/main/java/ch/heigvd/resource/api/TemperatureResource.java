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

@Path("/api/measures/temperature")
@Consumes("application/json")
@Produces("application/json")
public class TemperatureResource {

    @Inject
    TemperatureService temperatureService;

    @Inject
    PictureService pictureService;

    public record TemperatureRequest(Long userId, LocalDate date, String location, Integer degree) {}

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response createTemperatureMeasureWithPicture(@RestForm("request") @PartType(MediaType.APPLICATION_JSON) TemperatureRequest request,
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

    @Path("/{id}")
    @PUT
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
