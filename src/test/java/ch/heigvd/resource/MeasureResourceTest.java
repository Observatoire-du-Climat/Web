package ch.heigvd.resource;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.when;

@QuarkusTest
@TestHTTPEndpoint(MeasureResource.class)
public class MeasureResourceTest {

    @Inject
    EntityManager em;

    @Test
    public void testGetMeasure() {
        //when().get().then().statusCode(200).body();
    }

}
