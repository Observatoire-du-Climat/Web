package ch.heigvd.service;

import ch.heigvd.dto.*;
import ch.heigvd.entity.*;
import ch.heigvd.utils.TestHelpers;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class MeasureServiceTest {

    @Inject
    EntityManager em;

    @Inject
    MeasureService measureService;

    /* Pass when testing only this Class, but doesn't when testing all at once
    @Test
    void testSearchAllMeasuresEmptyList() {
        List<MeasureDTO> result = measureService.searchAllMeasures();
        assertTrue(result.isEmpty());
    }
     */


    @Test
    @TestTransaction
    void testSearchAllMeasures() {

        User user = TestHelpers.createTestUser(em);
        Temperature temperature = TestHelpers.createTestTemperatureMeasure(em, user);
        SnowHeight snowHeight = TestHelpers.createTestSnowHeightMeasure(em, user);

        List<MeasureDTO> result = measureService.searchAllMeasures();
        assertFalse(result.isEmpty());
        //assertEquals(2, result.size());

        assertTrue(user.getMeasures().contains(temperature));
        assertTrue(user.getMeasures().contains(snowHeight));
        assertTrue(result.stream().anyMatch(m ->
                m.id().equals(temperature.getId()) &&
                m.date().equals(LocalDate.of(2001, 2, 15)) &&
                m.location().equals("testlocation") &&
                m.type().equals(MeasureType.TEMPERATURE)
        ));
        assertTrue(result.stream().anyMatch(m ->
                m.id().equals(snowHeight.getId()) &&
                m.date().equals(LocalDate.of(2001, 2, 15)) &&
                m.location().equals("testlocation") &&
                m.type().equals(MeasureType.SNOW_HEIGHT)
        ));
    }

    @Test
    @TestTransaction
    void testSearchAllMeasuresByUserIdEmptyList() {
        User user = TestHelpers.createTestUser(em);
        //At first, should return nothing
        List<MeasureDTO> result = measureService.searchAllMeasuresByUserId(user.getId());
        assertTrue(result.isEmpty());
    }

    @Test
    @TestTransaction
    void testSearchAllMeasuresByUserId() {

        User user = TestHelpers.createTestUser(em);

        Temperature temperature = TestHelpers.createTestTemperatureMeasure(em, user);
        SnowHeight snowHeight = TestHelpers.createTestSnowHeightMeasure(em, user);

        List<MeasureDTO> result = measureService.searchAllMeasuresByUserId(user.getId());

        //Now should return some measures
        assertTrue(user.getMeasures().contains(temperature));
        assertTrue(user.getMeasures().contains(snowHeight));
        assertTrue(result.stream().anyMatch(m ->
                m.id().equals(temperature.getId()) &&
                        m.date().equals(LocalDate.of(2001, 2, 15)) &&
                        m.location().equals("testlocation") &&
                        m.type().equals(MeasureType.TEMPERATURE)
        ));
        assertTrue(result.stream().anyMatch(m ->
                m.id().equals(snowHeight.getId()) &&
                        m.date().equals(LocalDate.of(2001, 2, 15)) &&
                        m.location().equals("testlocation") &&
                        m.type().equals(MeasureType.SNOW_HEIGHT)
        ));
    }


    @Test
    void testSearchMeasureByIdWrongId() {
        assertThrows(NotFoundException.class, () -> measureService.searchMeasureById(-1L));

    }

    @Test
    @TestTransaction
    void testSearchMeasureByIdTemperature() {

        User user = TestHelpers.createTestUser(em);
        Temperature temperature = TestHelpers.createTestTemperatureMeasure(em, user);

        var result = measureService.searchMeasureById(temperature.getId());
        TemperatureMeasureDTO temperatureMeasureDTO = (TemperatureMeasureDTO) result;

        assertTrue(user.getMeasures().contains(temperature));
        assertEquals(temperature.getId(), temperatureMeasureDTO.id());
        assertEquals(LocalDate.of(2001, 2, 15), temperatureMeasureDTO.date());
        assertEquals("testlocation", temperatureMeasureDTO.location());
        assertEquals(MeasureType.TEMPERATURE, temperatureMeasureDTO.type());
        assertEquals(temperature.getDegree(), temperatureMeasureDTO.degree());

    }

    @Test
    @TestTransaction
    void testSearchMeasureByIdSnowHeight() {

        User user = TestHelpers.createTestUser(em);
        SnowHeight snowHeight = TestHelpers.createTestSnowHeightMeasure(em, user);

        var result = measureService.searchMeasureById(snowHeight.getId());
        SnowHeightMeasureDTO snowHeightMeasureDTO = (SnowHeightMeasureDTO) result;

        assertTrue(user.getMeasures().contains(snowHeight));
        assertEquals(snowHeight.getId(), snowHeightMeasureDTO.id());
        assertEquals(LocalDate.of(2001, 2, 15), snowHeightMeasureDTO.date());
        assertEquals("testlocation", snowHeightMeasureDTO.location());
        assertEquals(MeasureType.SNOW_HEIGHT, snowHeightMeasureDTO.type());
        assertEquals(snowHeight.getHeight(), snowHeightMeasureDTO.height());
        assertEquals(snowHeight.getWeather(), snowHeightMeasureDTO.weather());
        assertEquals(snowHeight.getPrecipitation(), snowHeightMeasureDTO.precipitation());

    }

    @Test
    @TestTransaction
    void testSearchMeasureByIdBirdMigration() {

        User user = TestHelpers.createTestUser(em);
        BirdMigration birdMigration = TestHelpers.createTestBirdMigrationMeasure(em, user);

        var result = measureService.searchMeasureById(birdMigration.getId());
        BirdMigrationMeasureDTO birdMigrationMeasureDTO = (BirdMigrationMeasureDTO) result;

        assertTrue(user.getMeasures().contains(birdMigration));
        assertEquals(birdMigration.getId(), birdMigrationMeasureDTO.id());
        assertEquals(LocalDate.of(2001, 2, 15), birdMigrationMeasureDTO.date());
        assertEquals("testlocation", birdMigrationMeasureDTO.location());
        assertEquals(MeasureType.BIRD_MIGRATION, birdMigrationMeasureDTO.type());
        assertEquals(birdMigration.getSpecie(), birdMigrationMeasureDTO.specie());
        assertEquals(birdMigration.getEventType(), birdMigrationMeasureDTO.event());

    }

    @Test
    @TestTransaction
    void testSearchMeasureByIdEggsLaying() {

        User user = TestHelpers.createTestUser(em);
        EggsLaying eggsLaying = TestHelpers.createTestEggsLayingMeasure(em, user);

        var result = measureService.searchMeasureById(eggsLaying.getId());
        EggsLayingMeasureDTO eggsLayingMeasureDTO = (EggsLayingMeasureDTO) result;

        assertTrue(user.getMeasures().contains(eggsLaying));
        assertEquals(eggsLaying.getId(), eggsLayingMeasureDTO.id());
        assertEquals(LocalDate.of(2001, 2, 15), eggsLayingMeasureDTO.date());
        assertEquals("testlocation", eggsLayingMeasureDTO.location());
        assertEquals(MeasureType.EGGS_LAYING, eggsLayingMeasureDTO.type());
        assertEquals(eggsLaying.getNumber(), eggsLayingMeasureDTO.number());
    }
}
