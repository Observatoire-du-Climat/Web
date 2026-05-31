package ch.heigvd.resource;

import ch.heigvd.dto.TemperatureMeasureDTO;
import ch.heigvd.service.TemperatureService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;

@Path("/measures/temperature")
@Consumes("application/json")
@Produces("application/json")
public class TemperatureResource {

    @Inject
    TemperatureService temperatureService;
    /*
    @Inject
    MeasureService measureService;
    */

    public record TemperatureRequest(Long userId, LocalDate date, String location, Integer degree) {}

    /*
    @Path("/{id}")
    @GET
    public Response getTemperatureMeasure(@PathParam("id") Long id) {
        try {
            var temperatureMeasureDTO = measureService.searchMeasureById(id);
            return Response.status(Response.Status.OK).entity(temperatureMeasureDTO).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
     */

    @POST
    public Response createTemperatureMeasure(TemperatureRequest request) {
        try {
            var temperatureMeasureDTO = temperatureService.addTemperature(request.userId(), request.date(), request.location(), request.degree());
            return Response.status(Response.Status.CREATED).entity(temperatureMeasureDTO).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
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
