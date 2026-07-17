package ch.heigvd.service;

import ch.heigvd.dto.TemperatureMeasureDTO;
import ch.heigvd.entity.*;
import ch.heigvd.utils.TestHelpers;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class TemperatureServiceTest {

    @Inject
    EntityManager em;

    @Inject
    TemperatureService temperatureService;

    @Test
    @TestTransaction
    void testAddTemperatureWrongUser() {
        assertThrows(NotFoundException.class, () ->
                temperatureService.addTemperature(
                        -1L,
                        LocalDate.of(2001, 2, 15),
                        "testlocation",
                        30
                )
        );
    }

    @Test
    @TestTransaction
    void testAddTemperatureNotValidUser() {
        User user = TestHelpers.createTestNotValidUser(em);
        assertThrows(ForbiddenException.class, () ->
                temperatureService.addTemperature(
                        user.getId(),
                        LocalDate.of(2001, 2, 15),
                        "testlocation",
                        30
                )
        );
    }

    @Test
    @TestTransaction
    void testAddTemperature() {

        User user = TestHelpers.createTestUser(em);
        TemperatureMeasureDTO result = temperatureService.addTemperature(
                user.getId(),
                LocalDate.of(2001, 2, 15),
                "testlocation",
                30);

        TemperatureMeasureDTO temperatureMeasureDTO = new TemperatureMeasureDTO(
                result.id(),
                LocalDate.of(2001, 2, 15),
                "testlocation",
                MeasureType.TEMPERATURE,
                "Test User",
                30);

        assertEquals(temperatureMeasureDTO, result);

        //Should be persisted
        Temperature temperature = em.find(Temperature.class, result.id());

        assertNotNull(temperature);
        assertEquals(30, temperature.getDegree());
        assertEquals(user.getId(), temperature.getUser().getId());
        assertTrue(user.getMeasures().contains(temperature));
    }

    @Test
    @TestTransaction
    void testModifyTemperatureWrongId() {
        assertThrows(NotFoundException.class, () ->
                temperatureService.modifyTemperatureById(
                        -1L,
                        LocalDate.of(2001, 2, 15),
                        "testlocation",
                        20
                )
        );
    }

    @Test
    @TestTransaction
    void testModifyTemperature() {

        User user = TestHelpers.createTestUser(em);
        TemperatureMeasureDTO firstTemperatureMeasureDTO = temperatureService.addTemperature(
                user.getId(),
                LocalDate.of(2001, 2, 15),
                "testlocation",
                30
        );

        TemperatureMeasureDTO result = temperatureService.modifyTemperatureById(
                firstTemperatureMeasureDTO.id(),
                LocalDate.of(2001, 2, 15),
                "testlocation",
                20
        );

        TemperatureMeasureDTO secondTemperatureMeasureDTO = new TemperatureMeasureDTO(
                result.id(),
                LocalDate.of(2001, 2, 15),
                "testlocation",
                MeasureType.TEMPERATURE,
                "Test User",
                20
        );

        assertNotEquals(firstTemperatureMeasureDTO, result);
        assertEquals(secondTemperatureMeasureDTO, result);
    }

    @Test
    @TestTransaction
    void testGetAllTemperatureMeasures() {
        User user = TestHelpers.createTestUser(em);
        SnowHeight snowHeight = TestHelpers.createTestSnowHeightMeasure(em, user);
        BirdMigration birdMigration = TestHelpers.createTestBirdMigrationMeasure(em, user);
        EggsLaying eggsLaying = TestHelpers.createTestEggsLayingMeasure(em, user);

        var results = temperatureService.getAllTemperatureMeasures();
        for (var result : results) {
            assertEquals(MeasureType.TEMPERATURE, result.type());
        }

    }
}
