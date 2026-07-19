package ch.heigvd.firebase;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Service responsible to send Notifications to the mobile application users
 */
@ApplicationScoped
public class NotificationService {

    /**
     * Send a push notification to the mobile application users
     * @param title the title of the notification
     * @param body the body of the notification
     * @return the id of the message created by Firebase
     * @throws FirebaseMessagingException if the sending failed
     */
    public String send(String title, String body) throws FirebaseMessagingException {

        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Message message = Message.builder()
                .setTopic("all-users")
                .setNotification(notification)
                .build();

        return FirebaseMessaging.getInstance().send(message);
    }
}
