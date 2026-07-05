package ch.heigvd.resource.admin;

import ch.heigvd.service.MeasureService;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("/admin/measure")
public class AdminMeasureResource {

    @Inject
    Template measure;

    @Inject
    MeasureService measureService;

    @GET
    @Path("/{id}")
    public TemplateInstance index(@PathParam("id") long id) {
        return measure.data("measure", measureService.searchMeasureById(id));
    }
}
