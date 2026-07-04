package ch.heigvd.resource;

import ch.heigvd.entity.User;
import ch.heigvd.resource.api.LoginResource;
import ch.heigvd.utils.TestResourceHelpers;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
@TestHTTPEndpoint(LoginResource.class)
public class LoginResourceTest {

    @Inject
    EntityManager em;

    @Test
    public void testLogin() {
        User user = TestResourceHelpers.createUserForTest(em);

        String body = """
        {
            "email": "%s",
            "password": "password"
        }
        """.formatted(user.getEmail());

        given().contentType("application/json")
                .body(body)
                .when()
                .post()
                .then()
                .statusCode(200)
                .body("id", equalTo(user.getId().intValue()))
                .body("name", equalTo(user.getName()))
                .body("email", equalTo(user.getEmail()));
    }

    @Test
    public void testLoginWrongPassword() {
        User user = TestResourceHelpers.createUserForTest(em);

        String body = """
        {
            "email": "%s",
            "password": "wrongpassword"
        }
        """.formatted(user.getEmail());

        given().contentType("application/json")
                .body(body)
                .when()
                .post()
                .then()
                .statusCode(401);
    }

    @Test
    public void testLoginWrongEmail() {

        String body = """
        {
            "email": "unknown@test.ch",
            "password": "password"
        }
        """;

        given().contentType("application/json")
                .body(body)
                .when()
                .post()
                .then()
                .statusCode(400);
    }

    @Test
    public void testLoginWrongBody() {

        String body = """
        {
            "email": "test@test.ch"
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
