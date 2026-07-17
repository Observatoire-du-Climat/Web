package ch.heigvd.resource.admin;

import ch.heigvd.entity.User;
import ch.heigvd.utils.TestResourceHelpers;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

@QuarkusTest
public class AdminMeasureResourceTest {

    @Inject
    EntityManager em;

    @Test
    void redirectToLoginWhenNotAuthenticated() {
        given()
                .when()
                .get("/admin/measure/1")
                .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "admin@test.ch", roles = "admin")
    void displayUnexistingMeasure() {
        given()
                .when()
                .get("/admin/measure/5555")
                .then()
                .statusCode(404);
    }

    @Test
    @TestSecurity(user = "admin@test.ch", roles = "admin")
    void displayAdminMeasurePageWhenAuthenticated() {

        User user = TestResourceHelpers.createUserForTest(em);
        TestResourceHelpers.createTestTemperatureMeasureForTest(em, user);

        given()
                .when()
                .get("/admin/measure/1")
                .then()
                .statusCode(200)
                .body(containsString("Détails de la mesure"));
    }
}
