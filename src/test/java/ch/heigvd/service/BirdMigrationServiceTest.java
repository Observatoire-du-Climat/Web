package ch.heigvd.service;

import ch.heigvd.dto.BirdMigrationMeasureDTO;
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
public class BirdMigrationServiceTest {

    @Inject
    EntityManager em;

    @Inject
    BirdMigrationService birdMigrationService;

    @Test
    @TestTransaction
    void testAddBirdMigrationWrongUser() {
        assertThrows(NotFoundException.class, () ->
                birdMigrationService.addBirdMigration(
                        -1L,
                        LocalDate.of(2001, 2, 15),
                        "testlocation",
                        "Swallow",
                        "Arrival"
                )
        );
    }

    @Test
    @TestTransaction
    void testAddBirdMigrationNotValidUser() {
        User user = TestHelpers.createTestNotValidUser(em);
        assertThrows(ForbiddenException.class, () ->
                birdMigrationService.addBirdMigration(
                        user.getId(),
                        LocalDate.of(2001, 2, 15),
                        "testlocation",
                        "Swallow",
                        "Arrival"
                )
        );
    }

    @Test
    @TestTransaction
    void testAddBirdMigration() {

        User user = TestHelpers.createTestUser(em);
        BirdMigrationMeasureDTO result = birdMigrationService.addBirdMigration(
                user.getId(),
                LocalDate.of(2001, 2, 15),
                "testlocation",
                "Swallow",
                "Arrival");

        BirdMigrationMeasureDTO birdMigrationMeasureDTO = new BirdMigrationMeasureDTO(
                result.id(),
                LocalDate.of(2001, 2, 15),
                "testlocation",
                MeasureType.BIRD_MIGRATION,
                "Test User",
                BirdSpecies.SWALLOW,
                BirdEventType.ARRIVAL
        );

        assertEquals(birdMigrationMeasureDTO, result);

        //Should be persisted
        BirdMigration birdMigration = em.find(BirdMigration.class, result.id());

        assertNotNull(birdMigration);
        assertEquals(BirdSpecies.SWALLOW, birdMigration.getSpecie());
        assertEquals(BirdEventType.ARRIVAL, birdMigration.getEventType());
        assertEquals(user.getId(), birdMigration.getUser().getId());
        assertTrue(user.getMeasures().contains(birdMigration));
    }

    @Test
    @TestTransaction
    void testModifyBirdMigrationWrongId() {
        assertThrows(NotFoundException.class, () ->
                birdMigrationService.modifyBirdMigrationById(
                        -1L,
                        LocalDate.of(2001, 2, 15),
                        "testlocation",
                        "Swift",
                        "Arrival"
                )
        );
    }

    @Test
    @TestTransaction
    void testModifyBirdMigration() {

        User user = TestHelpers.createTestUser(em);
        BirdMigrationMeasureDTO firstBirdMigrationMeasureDTO = birdMigrationService.addBirdMigration(
                user.getId(),
                LocalDate.of(2001, 2, 15),
                "testlocation",
                "Swallow",
                "Arrival"
        );

        BirdMigrationMeasureDTO result = birdMigrationService.modifyBirdMigrationById(
                firstBirdMigrationMeasureDTO.id(),
                LocalDate.of(2001, 2, 15),
                "testlocation",
                "Swift",
                "Departure"
        );

        BirdMigrationMeasureDTO secondBirdMigrationMeasureDTO = new BirdMigrationMeasureDTO(
                result.id(),
                LocalDate.of(2001, 2, 15),
                "testlocation",
                MeasureType.BIRD_MIGRATION,
                "Test User",
                BirdSpecies.SWIFT,
                BirdEventType.DEPARTURE
        );

        assertNotEquals(firstBirdMigrationMeasureDTO, result);
        assertEquals(secondBirdMigrationMeasureDTO, result);
    }

    @Test
    @TestTransaction
    void testGetAllBirdMigrationMeasures() {
        User user = TestHelpers.createTestUser(em);
        TestHelpers.createTestSnowHeightMeasure(em, user);
        TestHelpers.createTestTemperatureMeasure(em, user);
        TestHelpers.createTestEggsLayingMeasure(em, user);

        var results = birdMigrationService.getAllBirdMigrationMeasures();
        for (var result : results) {
            assertEquals(MeasureType.BIRD_MIGRATION, result.type());
        }

    }
}
