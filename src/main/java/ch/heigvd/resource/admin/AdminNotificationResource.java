package ch.heigvd.resource.admin;

import com.google.firebase.messaging.FirebaseMessagingException;
import ch.heigvd.firebase.NotificationService;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestForm;

import java.net.URI;

/**
 * Class responsible for a sub administration web page, displaying and sending push notifications.
 */
@RolesAllowed("admin")
@Path("/admin/notification")
public class AdminNotificationResource {

    @Inject
    Template notification;

    @Inject
    NotificationService notificationService;

    @Inject
    SecurityIdentity identity;

    /**
     * Display the notification page.
     * @param success if the sending was a success.
     * @param error if the sending was an error.
     * @return the rendered notification page.
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance index(@QueryParam("success") boolean success, @QueryParam("error") boolean error) {
        return  notification.data("success", success)
                .data("error", error)
                .data("adminName", identity.getPrincipal().getName());
    }

    /**
     * Send a push notifications to the mobile application users.
     * @param title the title of the notification.
     * @param body the body of the notification.
     * @return a redirection to the notification success or notification error page.
     */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/send")
    public Response sendNotification(@RestForm("title") String title, @RestForm("body") String body) {
        try {
            String normalizedTitle = title == null ? "" : title.trim();
            String normalizedBody = body == null ? "" : body.trim();

            notificationService.send(normalizedTitle, normalizedBody);
            return Response.seeOther(URI.create("/admin/notification?success=true")).build();

        } catch (FirebaseMessagingException e) {
            return Response.seeOther(URI.create("/admin/notification?error=true")).build();
        }
    }
}
