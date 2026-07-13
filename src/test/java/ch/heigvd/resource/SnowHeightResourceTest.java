package ch.heigvd.resource;

import ch.heigvd.entity.SnowHeight;
import ch.heigvd.entity.User;
import ch.heigvd.resource.api.SnowHeightResource;
import ch.heigvd.utils.TestResourceHelpers;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
@TestHTTPEndpoint(SnowHeightResource.class)
public class SnowHeightResourceTest {

    @Inject
    EntityManager em;

    @Test
    public void testCreateSnowHeightMeasure() {

        User user = TestResourceHelpers.createUserForTest(em);

        String requestJson = """
                {
                  "userId": %d,
                  "date": "2001-02-15",
                  "location": "testlocation",
                  "height": 10,
                  "weather": "Sunny",
                  "precipitation": 2
                }
                """.formatted(user.getId());

        given().multiPart("request", requestJson, "application/json")
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("date", equalTo("2001-02-15"))
                .body("location", equalTo("testlocation"))
                .body("type", equalTo("SNOW_HEIGHT"))
                .body("height", equalTo(10))
                .body("weather", equalTo("SUNNY"))
                .body("precipitation", equalTo(2));
    }

    @Test
    public void testCreateSnowHeightMeasureWrongUserId() {
        String requestJson = """
                {
                  "userId": -1,
                  "date": "2001-02-15",
                  "location": "testlocation",
                  "height": 10,
                  "weather": "Sunny",
                  "precipitation": 2
                }
                """;

        given().multiPart("request", requestJson, "application/json")
                .when()
                .post()
                .then()
                .statusCode(404);
    }

    @Test
    public void testCreateSnowHeightMeasureWrongBody() {
        User user = TestResourceHelpers.createUserForTest(em);

        String requestJson = """
                {
                  "userId": %d,
                  "date": "2001-02-15",
                  "location": "testlocation",
                  "height": 10,
                  "weather": "Sunny",
                }
                """.formatted(user.getId());

        given().multiPart("request", requestJson, "application/json")
                .when()
                .post()
                .then()
                .statusCode(400);
    }

    @Test
    public void testUpdateSnowHeightMeasure() {
        User user = TestResourceHelpers.createUserForTest(em);
        SnowHeight snowHeight = TestResourceHelpers.createTestSnowHeightMeasureForTest(em, user);

        String body = """
                {
                  "userId": %d,
                  "date": "2001-02-15",
                  "location": "newlocation",
                  "height": 20,
                  "weather": "Rainy",
                  "precipitation": 2
                }
                """.formatted(user.getId());

        given().contentType("application/json")
                .body(body)
                .when()
                .put("/" + snowHeight.getId())
                .then()
                .statusCode(200)
                .body("date", equalTo("2001-02-15"))
                .body("location", equalTo("newlocation"))
                .body("type", equalTo("SNOW_HEIGHT"))
                .body("height", equalTo(20))
                .body("weather", equalTo("RAINY"))
                .body("precipitation", equalTo(2));
    }

    @Test
    public void testUpdateSnowHeightMeasureWrongId() {
        User user = TestResourceHelpers.createUserForTest(em);
        SnowHeight snowHeight = TestResourceHelpers.createTestSnowHeightMeasureForTest(em, user);

        String body = """
                {
                  "userId": %d,
                  "date": "2001-02-15",
                  "location": "testlocation",
                  "height": 10,
                  "weather": "Sunny",
                  "precipitation": 2
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
    public void testUpdateSnowHeightMeasureWrongBody() {
        User user = TestResourceHelpers.createUserForTest(em);
        SnowHeight snowHeight = TestResourceHelpers.createTestSnowHeightMeasureForTest(em, user);

        String body = """
                {
                  "userId": %d,
                  "date": "2001-02-15",
                  "location": "testlocation",
                  "height": 10,
                  "weather": "Sunny",
                }
                """.formatted(user.getId());

        given().contentType("application/json")
                .body(body)
                .when()
                .put("/" + snowHeight.getId())
                .then()
                .statusCode(400);
    }
}
