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

@Path("/api/measures/eggs-laying")
@Consumes("application/json")
@Produces("application/json")
public class EggsLayingResource {

    @Inject
    EggsLayingService eggsLayingService;

    @Inject
    PictureService pictureService;

    public record EggsLayingRequest(Long userId, LocalDate date, String location, Integer number) {}


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
