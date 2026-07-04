package ch.heigvd.resource;

import ch.heigvd.entity.Measure;
import ch.heigvd.entity.User;
import ch.heigvd.resource.api.MeasureResource;
import ch.heigvd.utils.TestResourceHelpers;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@TestHTTPEndpoint(MeasureResource.class)
public class MeasureResourceTest {

    @Inject
    EntityManager em;



    @Test
    public void testGetMeasure() {
        User user = TestResourceHelpers.createUserForTest(em);
        Measure measure = TestResourceHelpers.createTestTemperatureMeasureForTest(em, user);

        given().contentType("application/json")
                .when()
                .get("/" + measure.getId())
                .then()
                .statusCode(200)
                .body("id", equalTo(measure.getId().intValue()))
                .body("date", equalTo(measure.getDate().toString()))
                .body("location", equalTo(measure.getLocation()))
                .body("type", equalTo(measure.getType().toString()));
    }

    @Test
    public void testGetMeasureWrongId() {
        given().contentType("application/json")
                .when()
                .get("/-1")
                .then()
                .statusCode(404);
    }

    @Test
    public void testGetUserMeasures() {
        User user = TestResourceHelpers.createUserForTest(em);
        Measure measure1 = TestResourceHelpers.createTestTemperatureMeasureForTest(em, user);
        Measure measure2 = TestResourceHelpers.createTestSnowHeightMeasureForTest(em, user);

        given().contentType("application/json")
                .when()
                .get("/user/" + user.getId())
                .then()
                .statusCode(200)
                .body("$", hasSize(2))
                .body("id", hasItems(measure1.getId().intValue(), measure2.getId().intValue()))
                .body("date", hasItems(measure1.getDate().toString(), measure2.getDate().toString()))
                .body("location", hasItems(measure1.getLocation(), measure2.getLocation()))
                .body("type", hasItems(measure1.getType().toString(), measure2.getType().toString()));
    }

    @Test
    public void testGetUserMeasuresWrongId() {
        given().contentType("application/json")
                .when()
                .get("/user/-1")
                .then()
                .statusCode(404);
    }

    @Test
    public void testDeleteMeasure() {
        User user = TestResourceHelpers.createUserForTest(em);
        Measure measure = TestResourceHelpers.createTestTemperatureMeasureForTest(em, user);
        given().contentType("application/json")
                .when()
                .delete("/" + measure.getId())
                .then()
                .statusCode(204);
    }

    @Test
    public void testDeleteMeasureWrongId() {
        given().contentType("application/json")
                .when()
                .delete("/-1")
                .then()
                .statusCode(404);
    }


}
