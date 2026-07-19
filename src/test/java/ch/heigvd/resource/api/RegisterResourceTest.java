package ch.heigvd.resource.api;

import ch.heigvd.entity.User;
import ch.heigvd.utils.TestResourceHelpers;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
@TestHTTPEndpoint(RegisterResource.class)
public class RegisterResourceTest {

    @Inject
    EntityManager em;

    @Test
    public void testRegister() {
        String body = """
        {
            "name": "Test User",
            "email": "test@test.ch",
            "password": "password"
        }
        """;

        given().contentType("application/json")
                .body(body)
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("name", equalTo("Test User"))
                .body("email", equalTo("test@test.ch"));
    }

    @Test
    public void testRegisterSameEmail() {
        User user = TestResourceHelpers.createUserForTest(em);

        String body = """
        {
            "name": "Test User",
            "email": "%s",
            "password": "password"
        }
        """.formatted(user.getEmail());

        given().contentType("application/json")
                .body(body)
                .when()
                .post()
                .then()
                .statusCode(400);

    }

    @Test
    public void testRegisterWrongBody() {
        String body = """
        {
            "name": "Test User",
            "email": "test@test.ch",
        }
        """;

        given().contentType("application/json")
                .body(body)
                .when()
                .post()
                .then()
                .statusCode(400);
    }

}
