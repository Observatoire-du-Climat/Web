package ch.heigvd.resource;

import ch.heigvd.entity.EggsLaying;
import ch.heigvd.entity.User;
import ch.heigvd.resource.api.EggsLayingResource;
import ch.heigvd.utils.TestResourceHelpers;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
@TestHTTPEndpoint(EggsLayingResource.class)
public class EggsLayingResourceTest {

    @Inject
    EntityManager em;

    @Test
    public void testCreateEggsLayingMeasure() {

        User user = TestResourceHelpers.createUserForTest(em);

        String body = """
                {
                  "userId": %d,
                  "date": "2001-02-15",
                  "location": "testlocation",
                  "number": 10
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
                .body("type", equalTo("EGGS_LAYING"))
                .body("number", equalTo(10));
    }

    @Test
    public void testCreateEggsLayingMeasureWrongUserId() {
        String body = """
                {
                  "userId": -1,
                  "date": "2001-02-15",
                  "location": "testlocation",
                  "number": 10
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
    public void testCreateEggsLayingMeasureWrongBody() {
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
    public void testUpdateEggsLayingMeasure() {
        User user = TestResourceHelpers.createUserForTest(em);
        EggsLaying eggsLaying = TestResourceHelpers.createTestEggsLayingMeasureForTest(em, user);

        String body = """
                {
                  "userId": %d,
                  "date": "2001-02-15",
                  "location": "newlocation",
                  "number": 20
                }
                """.formatted(user.getId());

        given().contentType("application/json")
                .body(body)
                .when()
                .put("/" + eggsLaying.getId())
                .then()
                .statusCode(200)
                .body("date", equalTo("2001-02-15"))
                .body("location", equalTo("newlocation"))
                .body("type", equalTo("EGGS_LAYING"))
                .body("number", equalTo(20));
    }

    @Test
    public void testUpdateEggsLayingMeasureWrongId() {
        User user = TestResourceHelpers.createUserForTest(em);
        EggsLaying eggsLaying = TestResourceHelpers.createTestEggsLayingMeasureForTest(em, user);

        String body = """
                {
                  "userId": %d,
                  "date": "2001-02-15",
                  "location": "newlocation",
                  "number": 20
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
    public void testUpdateEggsLayingMeasureWrongBody() {
        User user = TestResourceHelpers.createUserForTest(em);
        EggsLaying eggsLaying = TestResourceHelpers.createTestEggsLayingMeasureForTest(em, user);

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
                .put("/" + eggsLaying.getId())
                .then()
                .statusCode(400);
    }
}
