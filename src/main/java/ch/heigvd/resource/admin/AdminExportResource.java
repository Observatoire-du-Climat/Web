package ch.heigvd.resource.admin;

import ch.heigvd.service.ExportService;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RolesAllowed("admin")
@Path("/admin/export")
public class AdminExportResource {

    @Inject
    ExportService exportService;

    @Inject
    Template export;

    @Inject
    SecurityIdentity identity;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance index() {
        return export.data("adminName", identity.getPrincipal().getName());
    }

    @GET
    @Produces("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    @Path("/temperature")
    public Response exportTemperature() {
        byte[] file = exportService.exportTemperatureMeasure();
        return Response.status(Response.Status.OK)
                .entity(file)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"temperatures.xlsx\"")
                .build();
    }
}
