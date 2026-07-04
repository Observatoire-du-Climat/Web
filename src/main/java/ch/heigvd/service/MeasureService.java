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

@ApplicationScoped
public class MeasureService {

    private final EntityManager em;

    @Inject
    public MeasureService(EntityManager em) {
        this.em = em;
    }

    /**
     * Search all the measures in the database
     * @return all the measures
     */
    public List<MeasureDTO> searchAllMeasures() {
        var query = em.createQuery("""
            SELECT new ch.heigvd.dto.MeasureDTO(m.id, m.date, m.location, m.type)
            FROM Measure AS m
        """, MeasureDTO.class);

        return query.getResultList();
    }

    /**
     * Search all the measure of a specific user
     * @param userId the id of the user
     * @return the list of all measure taken by the specific user
     */
    public List<MeasureDTO> searchAllMeasuresByUserId(Long userId) {

        User user = em.find(User.class, userId);
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        var query = em.createQuery("""
            SELECT new ch.heigvd.dto.MeasureDTO(m.id, m.date, m.location, m.type)
            FROM Measure AS m
            WHERE m.user.id = :id
        """, MeasureDTO.class).setParameter("id", userId);

        return query.getResultList();
    }

    /**
     * Search a measure by its id
     * @param measureId the measure id
     * @return the specific measure with this id
     */
    public Object searchMeasureById(Long measureId) {

        Measure measure = em.find(Measure.class, measureId);
        if (measure == null) {
            throw new NotFoundException();
        }

        switch (measure.getType()) {
            case TEMPERATURE -> {
                var query = em.createQuery("""
                    SELECT new ch.heigvd.dto.TemperatureMeasureDTO(t.id, t.date, t.location, t.type, t.degree)
                    FROM Temperature AS t
                    WHERE t.id = :id
                """, TemperatureMeasureDTO.class).setParameter("id", measure.getId());
                return query.getSingleResult();
            }

            case SNOW_HEIGHT -> {
                var query = em.createQuery("""
                    SELECT new ch.heigvd.dto.SnowHeightMeasureDTO(s.id, s.date, s.location, s.type, s.height, s.weather, s.precipitation)
                    FROM SnowHeight AS s
                    WHERE s.id = :id
                """, SnowHeightMeasureDTO.class).setParameter("id", measure.getId());
                return query.getSingleResult();
            }

            case BIRD_MIGRATION -> {
                var query = em.createQuery("""
                    SELECT new ch.heigvd.dto.BirdMigrationMeasureDTO(b.id, b.date, b.location, b.type, b.specie, b.eventType)
                    FROM BirdMigration AS b
                    WHERE b.id = :id
                """, BirdMigrationMeasureDTO.class).setParameter("id", measure.getId());
                return query.getSingleResult();
            }

            case EGGS_LAYING -> {
                var query = em.createQuery("""
                    SELECT new ch.heigvd.dto.EggsLayingMeasureDTO(e.id, e.date, e.location, e.type, e.number)
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

    public List<MeasureDTO> getAllMeasures() {
        var query = em.createQuery("""
            SELECT new ch.heigvd.dto.MeasureDTO(m.id, m.date, m.location, m.type)
            FROM Measure AS m
        """, MeasureDTO.class);

        return query.getResultList();
    }

}
