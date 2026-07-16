package ch.heigvd.resource.admin;

import ch.heigvd.dto.MeasureDTO;
import ch.heigvd.service.MeasureService;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

/**
 * Class responsible for a sub administration web page, displaying a measure details.
 */
@RolesAllowed("admin")
@Path("/admin/measure")
public class AdminMeasureResource {

    @Inject
    Template measure;

    @Inject
    MeasureService measureService;

    @Inject
    SecurityIdentity identity;

    /**
     * Display the measure details page.
     * @param id the id of the measure.
     * @return the rendered measure details page.
     */
    @GET
    @Path("/{id}")
    public TemplateInstance index(@PathParam("id") long id) {
        return measure.data("measure", measureService.searchMeasureById(id))
                .data("adminName", identity.getPrincipal().getName());
    }

}
