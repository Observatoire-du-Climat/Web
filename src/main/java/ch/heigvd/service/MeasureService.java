package ch.heigvd.service;

import ch.heigvd.dto.*;
import ch.heigvd.entity.Measure;
import ch.heigvd.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service in charge of the Measure Class.
 * It contains the business logic associated with the Measure Entity and its subclasses.
 */
@ApplicationScoped
public class MeasureService {

    private final EntityManager em;

    @Inject
    public MeasureService(EntityManager em) {
        this.em = em;
    }

    /**
     * Get all the existing measures
     * It contains only the global details of the measures
     * @return a list of DTO from all the measures
     */
    public List<MeasureDTO> searchAllMeasures() {
        var query = em.createQuery("""
            SELECT new ch.heigvd.dto.MeasureDTO(m.id, m.date, m.location, m.type, m.user.name)
            FROM Measure AS m
        """, MeasureDTO.class);

        return query.getResultList();
    }

    /**
     * Search all the measure of a specific user
     * It contains only the global details of the measures
     * @param userId the id of the user
     * @return a list of DTO from all measure taken by the specific user
     * @throws NotFoundException if no measure possess this id
     */
    public List<MeasureDTO> searchAllMeasuresByUserId(Long userId) {

        User user = em.find(User.class, userId);
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        var query = em.createQuery("""
            SELECT new ch.heigvd.dto.MeasureDTO(m.id, m.date, m.location, m.type, m.user.name)
            FROM Measure AS m
            WHERE m.user.id = :id
            ORDER BY m.date
        """, MeasureDTO.class).setParameter("id", userId);

        return query.getResultList();
    }

    /**
     * Get a measure by its id
     * It retrieves all the details from the measures
     * @param measureId the measure id
     * @return the specific DTO of the measure with this id
     * @throws NotFoundException if no measure possess this id
     */
    public Object searchMeasureById(Long measureId) {

        Measure measure = em.find(Measure.class, measureId);
        if (measure == null) {
            throw new NotFoundException();
        }

        switch (measure.getType()) {
            case TEMPERATURE -> {
                var query = em.createQuery("""
                    SELECT new ch.heigvd.dto.TemperatureMeasureDTO(t.id, t.date, t.location, t.type, t.user.name, t.degree)
                    FROM Temperature AS t
                    WHERE t.id = :id
                """, TemperatureMeasureDTO.class).setParameter("id", measure.getId());
                return query.getSingleResult();
            }

            case SNOW_HEIGHT -> {
                var query = em.createQuery("""
                    SELECT new ch.heigvd.dto.SnowHeightMeasureDTO(s.id, s.date, s.location, s.type, s.user.name, s.height, s.weather, s.precipitation)
                    FROM SnowHeight AS s
                    WHERE s.id = :id
                """, SnowHeightMeasureDTO.class).setParameter("id", measure.getId());
                return query.getSingleResult();
            }

            case BIRD_MIGRATION -> {
                var query = em.createQuery("""
                    SELECT new ch.heigvd.dto.BirdMigrationMeasureDTO(b.id, b.date, b.location, b.type, b.user.name, b.specie, b.eventType)
                    FROM BirdMigration AS b
                    WHERE b.id = :id
                """, BirdMigrationMeasureDTO.class).setParameter("id", measure.getId());
                return query.getSingleResult();
            }

            case EGGS_LAYING -> {
                var query = em.createQuery("""
                    SELECT new ch.heigvd.dto.EggsLayingMeasureDTO(e.id, e.date, e.location, e.type, e.user.name, e.number)
                    FROM EggsLaying AS e
                    WHERE e.id = :id
                """, EggsLayingMeasureDTO.class).setParameter("id", measure.getId());
                return query.getSingleResult();
            }

            default -> throw new IllegalStateException("");
        }
    }

    /**
     * Delete a Measure
     * @param measureId the id of the measure to delete
     * @return true if the suppression was successful, false if not
     */
    @Transactional
    public Boolean deleteMeasureById(Long measureId) {

        Measure measureToDelete = em.find(Measure.class, measureId);
        if (measureToDelete == null) {
            return false;
        }

        User user = em.find(User.class, measureToDelete.getUser().getId());
        if (!user.getMeasures().remove(measureToDelete)) {
            //If the user doesn't have this measure in his measures set
            return false;
        }
        em.remove(measureToDelete);

        return true;
    }

    /**
     * Get all the existing measure, sorted by a given field
     * It contains only the global details of the measures
     * By default, sorted by id.
     * @param sort the field used to sort the measures
     * @return a list of DTO from all the measure
     */
    public List<MeasureDTO> getAllMeasures(String sort) {
        String orderBy;
        if (sort == null || sort.isBlank()) {
            orderBy = "m.id";
        } else {
            orderBy = switch (sort) {
                case "date" -> "m.date";
                case "type" -> "m.type";
                case "author" -> "m.user.name";
                default -> "m.id";
            };
        }

        String query = """
                SELECT new ch.heigvd.dto.MeasureDTO(m.id, m.date, m.location, m.type, m.user.name)
                FROM Measure AS m
                ORDER BY %s
                """.formatted(orderBy);
        return em.createQuery(query, MeasureDTO.class).getResultList();
    }

}
