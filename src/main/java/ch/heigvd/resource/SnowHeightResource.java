package ch.heigvd.resource;

import ch.heigvd.dto.SnowHeightMeasureDTO;
import ch.heigvd.service.MeasureService;
import ch.heigvd.service.SnowHeightService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;

@Path("/measures/snow-height")
@Consumes("application/json")
@Produces("application/json")
public class SnowHeightResource {

    @Inject
    SnowHeightService snowHeightService;

    @Inject
    MeasureService measureService;

    public record SnowHeightRequest(Long userId, LocalDate date, String location, Integer height, String weather, Integer precipitation) {}

    @Path("/{id}")
    @GET
    public Response getSnowHeightMeasure(@PathParam("id") Long id) {
        try {
            var snowHeightMeasureDTO = measureService.searchMeasureById(id);
            return Response.status(Response.Status.OK).entity(snowHeightMeasureDTO).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    public Response createSnowHeightMeasure(SnowHeightRequest request) {
        try {
            var snowHeightMeasureDTO = snowHeightService.addSnowHeight(request.userId(), request.date(), request.location(), request.height(), request.weather(), request.precipitation());
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
