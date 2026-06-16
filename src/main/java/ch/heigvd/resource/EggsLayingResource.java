package ch.heigvd.resource;

import ch.heigvd.dto.EggsLayingMeasureDTO;
import ch.heigvd.service.EggsLayingService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;

@Path("/api/measures/eggs-laying")
@Consumes("application/json")
@Produces("application/json")
public class EggsLayingResource {

    @Inject
    EggsLayingService eggsLayingService;
    /*
    @Inject
    MeasureService measureService;
     */

    public record EggsLayingRequest(Long userId, LocalDate date, String location, Integer number) {}
    /*
    @Path("/{id}")
    @GET
    public Response getEggsLayingMeasure(@PathParam("id") Long id) {
        try {
            var eggsLayingMeasureDTO = measureService.searchMeasureById(id);
            return Response.status(Response.Status.OK).entity(eggsLayingMeasureDTO).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
     */

    @POST
    public Response createEggsLayingMeasure(EggsLayingRequest request) {
        try {
            var eggsLayingMeasureDTO = eggsLayingService.addEggsLaying(request.userId, request.date, request.location, request.number);
            return Response.status(Response.Status.CREATED).entity(eggsLayingMeasureDTO).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Path("/{id}")
    @PUT
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
