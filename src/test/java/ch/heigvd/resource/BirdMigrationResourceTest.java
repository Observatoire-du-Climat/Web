package ch.heigvd.resource;

import ch.heigvd.entity.BirdMigration;
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
@TestHTTPEndpoint(BirdMigrationResource.class)
public class BirdMigrationResourceTest {

    @Inject
    EntityManager em;

    @Test
    public void testCreateBirdMigrationMeasure() {

        User user = TestResourceHelpers.createUserForTest(em);

        String body = """
                {
                  "userId": %d,
                  "date": "2001-02-15",
                  "location": "testlocation",
                  "birdType": "pigeon",
                  "arrival": true
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
                .body("type", equalTo("BIRD_MIGRATION"))
                .body("birdType", equalTo("pigeon"))
                .body("arrival", equalTo(true));
    }

    @Test
    public void testCreateBirdMigrationMeasureWrongUserId() {
        String body = """
                {
                  "userId": -1,
                  "date": "2001-02-15",
                  "location": "testlocation",
                  "birdType": "pigeon",
                  "arrival": true
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
    public void testCreateBirdMigrationMeasureWrongBody() {
        User user = TestResourceHelpers.createUserForTest(em);

        String body = """
                {
                  "userId": %d,
                  "date": "2001-02-15",
                  "location": "testlocation",
                  "birdType": "pigeon"
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
    public void testUpdateBirdMigrationMeasure() {
        User user = TestResourceHelpers.createUserForTest(em);
        BirdMigration birdMigration = TestResourceHelpers.createTestBirdMigrationForTest(em, user);

        String body = """
                {
                  "userId": %d,
                  "date": "2001-02-15",
                  "location": "newlocation",
                  "birdType": "hirondelle",
                  "arrival": true
                }
                """.formatted(user.getId());

        given().contentType("application/json")
                .body(body)
                .when()
                .put("/" + birdMigration.getId())
                .then()
                .statusCode(200)
                .body("date", equalTo("2001-02-15"))
                .body("location", equalTo("newlocation"))
                .body("type", equalTo("BIRD_MIGRATION"))
                .body("birdType", equalTo("hirondelle"))
                .body("arrival", equalTo(true));
    }

    @Test
    public void testUpdateBirdMigrationMeasureWrongId() {
        User user = TestResourceHelpers.createUserForTest(em);
        BirdMigration birdMigration = TestResourceHelpers.createTestBirdMigrationForTest(em, user);

        String body = """
                {
                  "userId": %d,
                  "date": "2001-02-15",
                  "location": "testlocation",
                  "birdType": "pigeon",
                  "arrival": true
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
        BirdMigration birdMigration = TestResourceHelpers.createTestBirdMigrationForTest(em, user);

        String body = """
                {
                  "userId": %d,
                  "date": "2001-02-15",
                  "location": "testlocation",
                  "birdType": "hirondelle",
                }
                """.formatted(user.getId());

        given().contentType("application/json")
                .body(body)
                .when()
                .put("/" + birdMigration.getId())
                .then()
                .statusCode(400);
    }
}
