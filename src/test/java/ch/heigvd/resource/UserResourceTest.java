package ch.heigvd.resource;

import ch.heigvd.entity.User;
import ch.heigvd.service.UserService;
import ch.heigvd.utils.TestHelpers;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
@TestHTTPEndpoint(UserResource.class)
public class UserResourceTest {

    @Inject
    EntityManager em;
    @Inject
    UserService userService;

    //Made to have the user insertion and REST test in different transactions
    //Otherwise the tests would not find the user in the database
    @Transactional
    User createUserForTest() {
        return TestHelpers.createTestUser(em);
    }

    @Test
    public void testGetUserById() {

        User user = createUserForTest();
        given().contentType("application/json")
                .when()
                .get("/" + user.getId())
                .then()
                .statusCode(200)
                .body("id", equalTo(user.getId().intValue()))
                .body("name", equalTo(user.getName()))
                .body("email", equalTo(user.getEmail()));
    }

    @Test
    public void testGetUserWrongId() {
        given().contentType("application/json")
                .when()
                .get("/-1")
                .then()
                .statusCode(404);
    }

    @Test
    public void testUpdateUser() {

        User user = createUserForTest();
        String body = """
            {
              "name": "New Name",
              "email": "new@test.ch",
              "password": "newpassword"
            }
            """;

        given().contentType("application/json")
                .body(body)
                .when()
                .put("/" + user.getId())
                .then()
                .statusCode(200)
                .body("id", equalTo(user.getId().intValue()))
                .body("name", equalTo("New Name"))
                .body("email", equalTo("new@test.ch"));

    }

    @Test
    public void testUpdateUserWrongId() {

        String body = """
            {
              "name": "New Name",
              "email": "new@test.ch",
              "password": "newpassword"
            }
            """;

        given().contentType("application/json")
                .body(body)
                .when()
                .put("/-1")
                .then()
                .statusCode(400);
    }

    @Test
    public void testUpdateUserNoBody() {
        User user = createUserForTest();

        given().contentType("application/json")
                .body("")
                .when()
                .put("/" + user.getId())
                .then()
                .statusCode(400);
    }

}
