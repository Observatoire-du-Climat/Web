package ch.heigvd.resource.api;

import ch.heigvd.dto.MeasureDTO;
import ch.heigvd.service.MeasureService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/measures")
@Consumes("application/json")
public class MeasureResource {

    @Inject
    MeasureService measureService;

    @Path("/user/{id}")
    @GET
    public Response getUserMeasures(@PathParam("id") Long id) {
        try {
            List<MeasureDTO> measureDTOs = measureService.searchAllMeasuresByUserId(id);
            return Response.status(Response.Status.OK).entity(measureDTOs).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Path("{id}")
    @GET
    public Response getMeasure(@PathParam("id") Long id) {
        try {
            var measureDTO = measureService.searchMeasureById(id);
            return Response.status(Response.Status.OK).entity(measureDTO).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Path("/{id}")
    @DELETE
    public Response deleteMeasure(@PathParam("id") Long id) {
        if (!measureService.deleteMeasureById(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}


