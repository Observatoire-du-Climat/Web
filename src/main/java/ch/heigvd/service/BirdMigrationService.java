package ch.heigvd.service;

import ch.heigvd.dto.BirdMigrationMeasureDTO;
import ch.heigvd.entity.BirdMigration;
import ch.heigvd.entity.MeasureType;
import ch.heigvd.entity.User;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.Optional;

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
     * @param birdType the bird type
     * @param arrival true if it is an arrival, false if departure
     * @return an Optional with the newly created BirdMigration if the insert was successful, or an empty Optional if insertion
     */
    public Optional<BirdMigrationMeasureDTO> addBirdMigration(Long userId, LocalDate date, String location, String birdType, Boolean arrival) {

        try {
            BirdMigration birdMigration = new BirdMigration();
            birdMigration.setDate(date);
            birdMigration.setLocation(location);
            birdMigration.setBirdType(birdType);
            birdMigration.setArrival(arrival);
            birdMigration.setType(MeasureType.BIRD_MIGRATION);

            User user = em.find(User.class, userId);
            birdMigration.setUser(user);
            user.getMeasures().add(birdMigration);

            em.persist(birdMigration);
            return Optional.of(new BirdMigrationMeasureDTO(birdMigration.getId(),
                    birdMigration.getDate(),
                    birdMigration.getLocation(),
                    birdMigration.getType().toString(),
                    birdMigration.getBirdType(),
                    birdMigration.getArrival()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Update a Bird Migration Measure
     * All the attribute are updated, even if not all of them are changed
     * @param birdMigration the measure to modify
     * @param date the (new) date of the measure
     * @param location the (new) location of the measure
     * @param birdType the (new) bird type of the measure
     * @param arrival the (new) arrival boolean of the measure
     * @return an Optional with the modified BirdMigration if the update was successful, or an empty Optional if update failed
     */
    public Optional<BirdMigrationMeasureDTO> modifyBirdMigration(BirdMigration birdMigration, LocalDate date, String location, String birdType, Boolean arrival) {

        try {

            BirdMigration birdMigrationToModify = em.find(BirdMigration.class, birdMigration.getId());
            birdMigrationToModify.setDate(date);
            birdMigrationToModify.setLocation(location);
            birdMigrationToModify.setBirdType(birdType);
            birdMigrationToModify.setArrival(arrival);
            return Optional.of(new BirdMigrationMeasureDTO(birdMigrationToModify.getId(),
                    birdMigrationToModify.getDate(),
                    birdMigrationToModify.getLocation(),
                    birdMigrationToModify.getType().toString(),
                    birdMigrationToModify.getBirdType(),
                    birdMigrationToModify.getArrival()));

        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
