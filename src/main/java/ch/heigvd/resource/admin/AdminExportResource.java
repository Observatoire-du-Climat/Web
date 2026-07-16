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

/**
 * Class responsible for a sub administration web page, exporting data into Excel files.
 */
@RolesAllowed("admin")
@Path("/admin/export")
public class AdminExportResource {

    @Inject
    ExportService exportService;

    @Inject
    Template export;

    @Inject
    SecurityIdentity identity;

    /**
     * Display the exporting page.
     * @return the rendered exporting page.
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance index() {
        return export.data("adminName", identity.getPrincipal().getName());
    }

    /**
     * Export the Temperature measures data into an Excel file.
     * @return a download of the Excel file.
     */
    @GET
    @Produces("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    @Path("/temperature")
    public Response exportTemperature() {
        byte[] file = exportService.exportTemperatureMeasures();
        if (file == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.OK)
                .entity(file)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"temperature.xlsx\"")
                .build();
    }

    /**
     * Export the SnowHeight measures data into an Excel file.
     * @return a download of the Excel file.
     */
    @GET
    @Produces("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    @Path("/snow-height")
    public Response exportSnowHeight() {
        byte[] file = exportService.exportSnowHeightMeasures();
        if (file == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.OK)
                .entity(file)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"hauteur-des-neiges.xlsx\"")
                .build();
    }

    /**
     * Export the BirdMigration measures data into an Excel file.
     * @return a download of the Excel file.
     */
    @GET
    @Produces("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    @Path("/bird-migration")
    public Response exportBirdMigration() {
        byte[] file = exportService.exportBirdMigrationMeasures();
        if (file == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.OK)
                .entity(file)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"migration-des-oiseaux.xlsx\"")
                .build();
    }

    /**
     * Export the EggsLaying measures data into an Excel file.
     * @return a download of the Excel file.
     */
    @GET
    @Produces("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    @Path("/eggs-laying")
    public Response exportEggsLaying() {
        byte[] file = exportService.exportEggsLayingMeasures();
        if (file == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.OK)
                .entity(file)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"relevé-des-pontes.xlsx\"")
                .build();
    }


}
