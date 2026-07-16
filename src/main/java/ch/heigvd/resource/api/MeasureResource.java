package ch.heigvd.resource.api;

import ch.heigvd.dto.MeasureDTO;
import ch.heigvd.service.MeasureService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

/**
 * Class responsible for the REST resource exposing global measure-related endpoints.
 */
@Path("/api/measures")
@Consumes(MediaType.APPLICATION_JSON)
public class MeasureResource {

    @Inject
    MeasureService measureService;

    /**
     * Get all the measure created by a User.
     * It contains the global details of each measure.
     * @param id the id of the user.
     * @return the appropriate HTTP Response, containing the measures if successful.
     */
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

    /**
     * Get a measure in details.
     * @param id the id of the measure.
     * @return the appropriate HTTP Response, containing the measure if successful.
     */
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

    /**
     * Delete a measure.
     * @param id the id of the measure to delete.
     * @return the appropriate HTTP Response
     */
    @Path("/{id}")
    @DELETE
    public Response deleteMeasure(@PathParam("id") Long id) {
        if (!measureService.deleteMeasureById(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}


