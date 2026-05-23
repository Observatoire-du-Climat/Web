package ch.heigvd.service;

import ch.heigvd.dto.BirdMigrationMeasureDTO;
import ch.heigvd.entity.BirdMigration;
import ch.heigvd.entity.MeasureType;
import ch.heigvd.entity.User;
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
                        "Pigeon",
                        true
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
                "Pigeon",
                true);

        BirdMigrationMeasureDTO birdMigrationMeasureDTO = new BirdMigrationMeasureDTO(
                result.id(),
                LocalDate.of(2001, 2, 15),
                "testlocation",
                MeasureType.BIRD_MIGRATION,
                "Pigeon",
                true
        );

        assertEquals(birdMigrationMeasureDTO, result);

        //Should be persisted
        BirdMigration birdMigration = em.find(BirdMigration.class, result.id());

        assertNotNull(birdMigration);
        assertEquals("Pigeon", birdMigration.getBirdType());
        assertTrue(birdMigration.getArrival());
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
                        "Hirondelle",
                        true
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
                "Pigeon",
                true
        );

        BirdMigrationMeasureDTO result = birdMigrationService.modifyBirdMigrationById(
                firstBirdMigrationMeasureDTO.id(),
                LocalDate.of(2001, 2, 15),
                "testlocation",
                "Hirondelle",
                true
        );

        BirdMigrationMeasureDTO secondBirdMigrationMeasureDTO = new BirdMigrationMeasureDTO(
                result.id(),
                LocalDate.of(2001, 2, 15),
                "testlocation",
                MeasureType.BIRD_MIGRATION,
                "Hirondelle",
                true
        );

        assertNotEquals(firstBirdMigrationMeasureDTO, result);
        assertEquals(secondBirdMigrationMeasureDTO, result);
    }
}
