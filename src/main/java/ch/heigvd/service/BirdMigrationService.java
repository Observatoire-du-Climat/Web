package ch.heigvd.service;

import ch.heigvd.dto.BirdMigrationMeasureDTO;
import ch.heigvd.entity.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDate;
import java.util.Optional;

@ApplicationScoped
public class BirdMigrationService {

    private final EntityManager em;

    @Inject
    public BirdMigrationService(EntityManager em) {
        this.em = em;
    }

    /**
     * Insert a Bird Migration Measure
     * @param userId the id of the user that created the measure
     * @param date the date of the measure
     * @param location the location of the measure
     * @param specie the specie of the bird
     * @param event departure or arrival
     * @return a DTO with the newly created BirdMigration if the insert was successful
     */
    @Transactional
    public BirdMigrationMeasureDTO addBirdMigration(Long userId, LocalDate date, String location, String specie, String event) {

        BirdMigration birdMigration = new BirdMigration();
        birdMigration.setDate(date);
        birdMigration.setLocation(location);
        birdMigration.setSpecie(BirdSpecies.valueOf(specie.toUpperCase()));
        birdMigration.setEventType(BirdEventType.valueOf(event.toUpperCase()));
        birdMigration.setType(MeasureType.BIRD_MIGRATION);

        User user = em.find(User.class, userId);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        birdMigration.setUser(user);
        user.getMeasures().add(birdMigration);

        em.persist(birdMigration);
        return new BirdMigrationMeasureDTO(birdMigration.getId(),
                birdMigration.getDate(),
                birdMigration.getLocation(),
                birdMigration.getType(),
                birdMigration.getSpecie(),
                birdMigration.getEventType());
    }

    /**
     * Update a Bird Migration Measure
     * All the attribute are updated, even if not all of them are changed
     * @param measureId the id of the measure to modify
     * @param date the (new) date of the measure
     * @param location the (new) location of the measure
     * @param specie the (new) bird specie of the measure
     * @param event the (new) event of the measure
     * @return a DTO with the modified BirdMigration if the update was successful
     */
    @Transactional
    public BirdMigrationMeasureDTO modifyBirdMigrationById(Long measureId, LocalDate date, String location, String specie, String event) {

        BirdMigration birdMigrationToModify = em.find(BirdMigration.class, measureId);
        if (birdMigrationToModify == null) {
            throw new NotFoundException("Measure not found");
        }
        birdMigrationToModify.setDate(date);
        birdMigrationToModify.setLocation(location);
        birdMigrationToModify.setSpecie(BirdSpecies.valueOf(specie.toUpperCase()));
        birdMigrationToModify.setEventType(BirdEventType.valueOf(event.toUpperCase()));
        return new BirdMigrationMeasureDTO(birdMigrationToModify.getId(),
                birdMigrationToModify.getDate(),
                birdMigrationToModify.getLocation(),
                birdMigrationToModify.getType(),
                birdMigrationToModify.getSpecie(),
                birdMigrationToModify.getEventType());
    }
}
