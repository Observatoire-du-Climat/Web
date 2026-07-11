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

@Path("/api/measures/snow-height")
@Consumes("application/json")
@Produces("application/json")
public class SnowHeightResource {

    @Inject
    SnowHeightService snowHeightService;

    @Inject
    PictureService pictureService;

    public record SnowHeightRequest(Long userId, LocalDate date, String location, Integer height, String weather, Integer precipitation) {}

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
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Path("/{id}")
    @PUT
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
