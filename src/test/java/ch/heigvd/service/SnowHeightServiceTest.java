package ch.heigvd.service;

import ch.heigvd.dto.SnowHeightMeasureDTO;
import ch.heigvd.entity.MeasureType;
import ch.heigvd.entity.SnowHeight;
import ch.heigvd.entity.User;
import ch.heigvd.entity.WeatherType;
import ch.heigvd.utils.TestHelpers;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class SnowHeightServiceTest {


    @Inject
    EntityManager em;

    @Inject
    SnowHeightService snowHeightService;

    @Test
    @TestTransaction
    void testAddSnowHeightWrongUser() {
        assertThrows(NotFoundException.class, () ->
                snowHeightService.addSnowHeight(
                        -1L,
                        LocalDate.of(2001, 2, 15),
                        "testlocation",
                        10,
                        "Sunny",
                        2
                )
        );
    }

    @Test
    @TestTransaction
    void testAddSnowHeight() {

        User user = TestHelpers.createTestUser(em);
        SnowHeightMeasureDTO result = snowHeightService.addSnowHeight(
                user.getId(),
                LocalDate.of(2001, 2, 15),
                "testlocation",
                10,
                "Sunny",
                2
        );

        SnowHeightMeasureDTO snowHeightMeasureDTO = new SnowHeightMeasureDTO(
                result.id(),
                LocalDate.of(2001, 2, 15),
                "testlocation",
                MeasureType.SNOW_HEIGHT,
                "Test User",
                10,
                WeatherType.SUNNY,
                2
        );

        assertEquals(snowHeightMeasureDTO, result);

        //Should be persisted
        SnowHeight snowHeight = em.find(SnowHeight.class, result.id());

        assertNotNull(snowHeight);
        assertEquals(10, snowHeight.getHeight());
        assertEquals(WeatherType.SUNNY, snowHeight.getWeather());
        assertEquals(2, snowHeight.getPrecipitation());
        assertEquals(user.getId(), snowHeight.getUser().getId());
        assertTrue(user.getMeasures().contains(snowHeight));
    }

    @Test
    @TestTransaction
    void testModifySnowHeightWrongId() {
        assertThrows(NotFoundException.class, () ->
                snowHeightService.modifySnowHeightById(
                        -1L,
                        LocalDate.of(2001, 2, 15),
                        "testlocation",
                        20,
                        "Sunny",
                        2
                )
        );
    }

    @Test
    @TestTransaction
    void testModifySnowHeight() {

        User user = TestHelpers.createTestUser(em);
        SnowHeightMeasureDTO firstSnowHeightMeasureDTO = snowHeightService.addSnowHeight(
                user.getId(),
                LocalDate.of(2001, 2, 15),
                "testlocation",
                10,
                "Sunny",
                2
        );

        SnowHeightMeasureDTO result = snowHeightService.modifySnowHeightById(
                firstSnowHeightMeasureDTO.id(),
                LocalDate.of(2001, 2, 15),
                "testlocation",
                20,
                "Sunny",
                2
        );

        SnowHeightMeasureDTO secondSnowHeightMeasureDTO = new SnowHeightMeasureDTO(
                result.id(),
                LocalDate.of(2001, 2, 15),
                "testlocation",
                MeasureType.SNOW_HEIGHT,
                "Test User",
                20,
                WeatherType.SUNNY,
                2
        );

        assertNotEquals(firstSnowHeightMeasureDTO, result);
        assertEquals(secondSnowHeightMeasureDTO, result);
    }
}
