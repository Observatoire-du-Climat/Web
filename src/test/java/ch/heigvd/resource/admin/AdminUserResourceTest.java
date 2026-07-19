package ch.heigvd.resource.admin;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

@QuarkusTest
public class AdminUserResourceTest {

    @Test
    void redirectToLoginWhenNotAuthenticated() {
        given()
                .when()
                .get("/admin/user/1")
                .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "admin@test.ch", roles = "admin")
    void displayUnexistingUser() {
        given()
                .when()
                .get("/admin/user/5555")
                .then()
                .statusCode(404);
    }

    @Test
    @TestSecurity(user = "admin@test.ch", roles = "admin")
    void displayAdminUserPageWhenAuthenticated() {
        given()
                .when()
                .get("/admin/user/1")
                .then()
                .statusCode(200)
                .body(containsString("Informations utilisateur"));
    }

}
