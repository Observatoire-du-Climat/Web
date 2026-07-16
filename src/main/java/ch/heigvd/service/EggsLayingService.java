package ch.heigvd.service;

import ch.heigvd.dto.EggsLayingMeasureDTO;
import ch.heigvd.entity.EggsLaying;
import ch.heigvd.entity.MeasureType;
import ch.heigvd.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service in charge of the EggsLaying Class.
 * It contains the business logic associated with the EggsLaying Entity.
 */
@ApplicationScoped
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
     * @return a DTO with the newly created EggsLaying if the insert was successful
     * @throws NotFoundException if no user possess this userId
     * @throws ForbiddenException if the user does not have the rights to create a Measure.
     */
    @Transactional
    public EggsLayingMeasureDTO addEggsLaying(Long userId, LocalDate date, String location, Integer number) {

        EggsLaying eggsLaying = new EggsLaying();
        eggsLaying.setDate(date);
        eggsLaying.setLocation(location);
        eggsLaying.setNumber(number);
        eggsLaying.setType(MeasureType.EGGS_LAYING);

        User user = em.find(User.class, userId);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        if (!user.getValid()) {
            throw new ForbiddenException("User not valid");
        }
        eggsLaying.setUser(user);
        user.getMeasures().add(eggsLaying);

        em.persist(eggsLaying);
        return new EggsLayingMeasureDTO(eggsLaying.getId(),
                eggsLaying.getDate(),
                eggsLaying.getLocation(),
                eggsLaying.getType(),
                eggsLaying.getUser().getName(),
                eggsLaying.getNumber());
    }

    /**
     * Update a Bird Migration Measure
     * All the attribute are updated, even if not all of them are changed
     * @param measureId the id of the measure to modify
     * @param date the (new) date of the measure
     * @param location the (new) location of the measure
     * @param number the (new) number value of the measure
     * @return a DTO with the modified EggsLaying if the update was successful
     * @throws NotFoundException if no measure possess this measureId
     */
    @Transactional
    public EggsLayingMeasureDTO modifyEggsLayingById(Long measureId, LocalDate date, String location, Integer number) {

        EggsLaying eggsLayingToModify = em.find(EggsLaying.class, measureId);
        if (eggsLayingToModify == null) {
            throw new NotFoundException("Measure not found");
        }
        eggsLayingToModify.setDate(date);
        eggsLayingToModify.setLocation(location);
        eggsLayingToModify.setNumber(number);
        return new EggsLayingMeasureDTO(eggsLayingToModify.getId(),
                eggsLayingToModify.getDate(),
                eggsLayingToModify.getLocation(),
                eggsLayingToModify.getType(),
                eggsLayingToModify.getUser().getName(),
                eggsLayingToModify.getNumber());
    }

    /**
     * Get all the existing EggsLaying measures in details.
     * @return a list of DTO from all the EggsLaying measures.
     */
    public List<EggsLayingMeasureDTO> getAllEggsLayingMeasure() {
        var query = em.createQuery("""
                    SELECT new ch.heigvd.dto.EggsLayingMeasureDTO(e.id, e.date, e.location, e.type, e.user.name, e.number)
                    FROM EggsLaying AS e
                """, EggsLayingMeasureDTO.class);

        return query.getResultList();
    }
}
