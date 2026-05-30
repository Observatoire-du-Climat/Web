package ch.heigvd.resource;

import ch.heigvd.entity.Temperature;
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
@TestHTTPEndpoint(TemperatureResource.class)
public class TemperatureResourceTest {

    @Inject
    EntityManager em;

    @Test
    public void testCreateTemperatureMeasure() {

        User user = TestResourceHelpers.createUserForTest(em);

        String body = """
                {
                  "userId": %d,
                  "date": "2001-02-15",
                  "location": "testlocation",
                  "degree": 30
                }
                """.formatted(user.getId());

        given().contentType("application/json")
                .body(body)
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("date", equalTo("2001-02-15"))
                .body("location", equalTo("testlocation"))
                .body("type", equalTo("TEMPERATURE"))
                .body("degree", equalTo(30));
    }

    @Test
    public void testCreateTemperatureMeasureWrongUserId() {
        String body = """
                {
                  "userId": -1,
                  "date": "2001-02-15",
                  "location": "testlocation",
                  "degree": 30
                }
                """;

        given().contentType("application/json")
                .body(body)
                .when()
                .post()
                .then()
                .statusCode(404);
    }

    @Test
    public void testCreateTemperatureMeasureWrongBody() {
        User user = TestResourceHelpers.createUserForTest(em);

        String body = """
                {
                  "userId": %d,
                  "date": "2001-02-15",
                  "location": "testlocation"
                }
                """.formatted(user.getId());

        given().contentType("application/json")
                .body(body)
                .when()
                .post()
                .then()
                .statusCode(400);
    }

    @Test
    public void testUpdateTemperatureMeasure() {
        User user = TestResourceHelpers.createUserForTest(em);
        Temperature temperature = TestResourceHelpers.createTestTemperatureMeasureForTest(em, user);

        String body = """
                {
                  "userId": %d,
                  "date": "2001-02-15",
                  "location": "newlocation",
                  "degree": 20
                }
                """.formatted(user.getId());

        given().contentType("application/json")
                .body(body)
                .when()
                .put("/" + temperature.getId())
                .then()
                .statusCode(200)
                .body("date", equalTo("2001-02-15"))
                .body("location", equalTo("newlocation"))
                .body("type", equalTo("TEMPERATURE"))
                .body("degree", equalTo(20));
    }

    @Test
    public void testUpdateTemperatureMeasureWrongId() {
        User user = TestResourceHelpers.createUserForTest(em);
        Temperature temperature = TestResourceHelpers.createTestTemperatureMeasureForTest(em, user);

        String body = """
                {
                  "userId": %d,
                  "date": "2001-02-15",
                  "location": "newlocation",
                  "degree": 20
                }
                """.formatted(user.getId());

        given().contentType("application/json")
                .body(body)
                .when()
                .put("/-1")
                .then()
                .statusCode(404);

    }

    @Test
    public void testUpdateTemperatureMeasureWrongBody() {
        User user = TestResourceHelpers.createUserForTest(em);
        Temperature temperature = TestResourceHelpers.createTestTemperatureMeasureForTest(em, user);

        String body = """
                {
                  "userId": %d,
                  "date": "2001-02-15",
                  "location": "newlocation"
                }
                """.formatted(user.getId());

        given().contentType("application/json")
                .body(body)
                .when()
                .put("/" + temperature.getId())
                .then()
                .statusCode(400);
    }


}
