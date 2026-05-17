package ch.heigvd.service;

import ch.heigvd.dto.EggsLayingMeasureDTO;
import ch.heigvd.entity.EggsLaying;
import ch.heigvd.entity.MeasureType;
import ch.heigvd.entity.User;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.Optional;

public class EggsLayingService {

    private final EntityManager em;

    @Inject
    public EggsLayingService(EntityManager em) {
        this.em = em;
    }

    /**
     * Insert an EggsLaying Measure
     * @param userId the id of the user that created the measure
     * @param date the date of the measure
     * @param location the location of the measure
     * @param number the number value of the measure
     * @return an Optional with the newly created EggsLaying if the insert was successful, or an empty Optional if insertion
     */
    public Optional<EggsLayingMeasureDTO> addEggsLaying(Long userId, LocalDate date, String location, Integer number) {

        try {
            EggsLaying eggsLaying = new EggsLaying();
            eggsLaying.setDate(date);
            eggsLaying.setLocation(location);
            eggsLaying.setNumber(number);
            eggsLaying.setType(MeasureType.EGGS_LAYING);

            User user = em.find(User.class, userId);
            eggsLaying.setUser(user);
            user.getMeasures().add(eggsLaying);

            em.persist(eggsLaying);
            return Optional.of(new EggsLayingMeasureDTO(eggsLaying.getId(),
                    eggsLaying.getDate(),
                    eggsLaying.getLocation(),
                    eggsLaying.getType().toString(),
                    eggsLaying.getNumber()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Update a Bird Migration Measure
     * All the attribute are updated, even if not all of them are changed
     * @param eggsLaying the measure to modify
     * @param date the (new) date of the measure
     * @param location the (new) location of the measure
     * @param number the (new) number value of the measure
     * @return an Optional with the modified EggsLaying if the update was successful, or an empty Optional if update failed
     */
    public Optional<EggsLayingMeasureDTO> modifyEggsLaying(EggsLaying eggsLaying, LocalDate date, String location, Integer number) {

        try {

            EggsLaying eggsLayingToModify = em.find(EggsLaying.class, eggsLaying.getId());
            eggsLayingToModify.setDate(date);
            eggsLayingToModify.setLocation(location);
            eggsLayingToModify.setNumber(number);
            return Optional.of(new EggsLayingMeasureDTO(eggsLayingToModify.getId(),
                    eggsLayingToModify.getDate(),
                    eggsLayingToModify.getLocation(),
                    eggsLayingToModify.getType().toString(),
                    eggsLayingToModify.getNumber()));

        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
