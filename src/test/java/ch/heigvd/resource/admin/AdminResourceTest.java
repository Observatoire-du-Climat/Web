package ch.heigvd.resource.admin;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import io.quarkus.test.security.TestSecurity;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class AdminResourceTest {

    @Test
    void redirectToLoginWhenNotAuthenticated() {
        given()
                .when()
                .get("/admin")
                .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "admin@test.ch", roles = "admin")
    void displayAdminPageWhenAuthenticated() {
        given()
                .when()
                .get("/admin")
                .then()
                .statusCode(200)
                .body(containsString("Portail Administrateur"));
    }
}
