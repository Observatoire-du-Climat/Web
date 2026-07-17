package ch.heigvd.service;

import ch.heigvd.dto.EggsLayingMeasureDTO;
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
public class EggsLayingServiceTest {

    @Inject
    EntityManager em;

    @Inject
    EggsLayingService eggsLayingService;

    @Test
    @TestTransaction
    void testAddEggsLayingWrongUser() {
        assertThrows(NotFoundException.class, () ->
                eggsLayingService.addEggsLaying(
                        -1L,
                        LocalDate.of(2001, 2, 15),
                        "testlocation",
                        10
                )
        );
    }

    @Test
    @TestTransaction
    void testAddEggsLayingNotValidUser() {
        User user = TestHelpers.createTestNotValidUser(em);
        assertThrows(ForbiddenException.class, () ->
                eggsLayingService.addEggsLaying(
                        user.getId(),
                        LocalDate.of(2001, 2, 15),
                        "testlocation",
                        10
                )
        );
    }

    @Test
    @TestTransaction
    void testAddEggsLaying() {

        User user = TestHelpers.createTestUser(em);
        EggsLayingMeasureDTO result = eggsLayingService.addEggsLaying(
                user.getId(),
                LocalDate.of(2001, 2, 15),
                "testlocation",
                10);

        EggsLayingMeasureDTO eggsLayingMeasureDTO = new EggsLayingMeasureDTO(
                result.id(),
                LocalDate.of(2001, 2, 15),
                "testlocation",
                MeasureType.EGGS_LAYING,
                "Test User",
                10);

        assertEquals(eggsLayingMeasureDTO, result);

        //Should be persisted
        EggsLaying eggsLaying = em.find(EggsLaying.class, result.id());

        assertNotNull(eggsLaying);
        assertEquals(10, eggsLaying.getNumber());
        assertEquals(user.getId(), eggsLaying.getUser().getId());
        assertTrue(user.getMeasures().contains(eggsLaying));
    }

    @Test
    @TestTransaction
    void testModifyEggsLayingWrongId() {
        assertThrows(NotFoundException.class, () ->
                eggsLayingService.modifyEggsLayingById(
                        -1L,
                        LocalDate.of(2001, 2, 15),
                        "testlocation",
                        20
                )
        );
    }

    @Test
    @TestTransaction
    void testModifyEggsLaying() {

        User user = TestHelpers.createTestUser(em);
        EggsLayingMeasureDTO firstEggsLayingMeasureDTO = eggsLayingService.addEggsLaying(
                user.getId(),
                LocalDate.of(2001, 2, 15),
                "testlocation",
                10
        );

        EggsLayingMeasureDTO result = eggsLayingService.modifyEggsLayingById(
                firstEggsLayingMeasureDTO.id(),
                LocalDate.of(2001, 2, 15),
                "testlocation",
                20
        );

        EggsLayingMeasureDTO secondEggsLayingMeasureDTO = new EggsLayingMeasureDTO(
                result.id(),
                LocalDate.of(2001, 2, 15),
                "testlocation",
                MeasureType.EGGS_LAYING,
                "Test User",
                20
        );

        assertNotEquals(firstEggsLayingMeasureDTO, result);
        assertEquals(secondEggsLayingMeasureDTO, result);
    }


    @Test
    @TestTransaction
    void testGetAllEggsLayingMeasures() {
        User user = TestHelpers.createTestUser(em);
        TestHelpers.createTestSnowHeightMeasure(em, user);
        TestHelpers.createTestBirdMigrationMeasure(em, user);
        TestHelpers.createTestTemperatureMeasure(em, user);

        var results = eggsLayingService.getAllEggsLayingMeasure();
        for (var result : results) {
            assertEquals(MeasureType.EGGS_LAYING, result.type());
        }

    }
}
