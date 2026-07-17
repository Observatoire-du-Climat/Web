package ch.heigvd.resource.api;

import ch.heigvd.dto.MeasurePictureDTO;
import ch.heigvd.service.PictureService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import java.io.File;
import java.nio.file.Files;

/**
 * Class responsible for the REST resource exposing MeasurePicture-related endpoints.
 */
@Path("/api/measures")
public class PictureResource {

    @Inject
    PictureService pictureService;

    /**
     * Get the picture of a measure.
      * @param measureId the id of the measure
     * @return the appropriate HTTP Response, containing the picture if successful.
     */
    @GET
    @Path("/{id}/picture")
    public Response getPicture(@PathParam("id") long measureId) {
        try {
            MeasurePictureDTO measurePictureDTO = pictureService.getPictureByMeasureId(measureId);

            File picture = new File(measurePictureDTO.path());
            if (!picture.exists() || !picture.isFile()) {
                throw new NotFoundException("Picture not found");
            }
            String contentType = Files.probeContentType(picture.toPath());
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return Response.status(Response.Status.OK).entity(picture).type(contentType).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
