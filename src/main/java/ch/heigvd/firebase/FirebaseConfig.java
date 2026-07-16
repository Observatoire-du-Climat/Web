package ch.heigvd.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Class to help the configuration of Firebase Cloud Messaging
 * It is launched at the start of the application
 */
@Startup
@ApplicationScoped
public class FirebaseConfig {

    @ConfigProperty(name = "firebase.credentials.path")
    String credentialsPath;

    @ConfigProperty(name = "firebase.enabled", defaultValue = "true")
    boolean firebaseEnabled;

    /**
     * Initialize Firebase at the start of the application
     */
    @PostConstruct
    void initializeFirebase() {

        //to not try to launch firebase in test mode, specially for the ci/cd pipeline on github
        if (!firebaseEnabled) {
            return;
        }

        if (!FirebaseApp.getApps().isEmpty()) {
            return;
        }

        try (InputStream serviceAccount = new FileInputStream(credentialsPath)) {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            throw new IllegalStateException("Impossible d'initialiser Firebase avec " + credentialsPath);
        }
    }
}
