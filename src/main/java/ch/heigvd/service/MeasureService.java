package ch.heigvd.service;

import ch.heigvd.entity.Measure;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class MeasureService {

    private EntityManager em;

    @Inject
    public MeasureService(EntityManager em) {
        this.em = em;
    }

    public record MeasureDTO(Long id, LocalDate date, String location, String type) {}
    public record TemperatureMeasureDTO(Long id, LocalDate date, String location, String type, Integer degree) {}
    public record SnowHeightMeasureDTO(Long id, LocalDate date, String location, String type, Integer height, String weather, Integer precipitation) {}
    public record BirdMigrationMeasureDTO(Long id, LocalDate date, String location, String type, String birdType, Boolean arrival) {}
    public record EggsLayingMeasureDTO(Long id, LocalDate date, String location, String type, Integer number) {}

    /**
     * Search all the measures in the database
     * @return all the measures
     */
    public List<MeasureDTO> searchAllMeasures() {
        var query = em.createQuery("""
            SELECT m.id, m.date, m.location, m.type
            FROM Measure AS m
        """, MeasureDTO.class);

        return query.getResultList();
    }

    public List<MeasureDTO> searchAllMeasuresByUserId(Long userId) {
        var query = em.createQuery("""
            SELECT m.id, m.date, m.location, m.type
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
            case "TEMPERATURE" -> {
                var query = em.createQuery("""
                    SELECT t.id, t.date, t.location, t.degree
                    FROM Temperature AS t
                    WHERE t.id = :id
                """, TemperatureMeasureDTO.class).setParameter("id", measure.getId());
                return query.getSingleResult();
            }

            case "SNOW_HEIGHT" -> {
                var query = em.createQuery("""
                    SELECT s.id, s.date, s.location, s.height, s.weather, s.precipitation
                    FROM SnowHeight AS s
                    WHERE s.id = :id
                """, SnowHeightMeasureDTO.class).setParameter("id", measure.getId());
                return query.getSingleResult();
            }

            case "BIRD_MIGRATION" -> {
                var query = em.createQuery("""
                    SELECT b.id, b.date, b.location, b.birdType, b.arrival
                    FROM BirdMigration AS b
                    WHERE b.id = :id
                """, BirdMigrationMeasureDTO.class).setParameter("id", measure.getId());
                return query.getSingleResult();
            }

            case "EGGS_LAYING" -> {
                var query = em.createQuery("""
                    SELECT e.id, e.date, e.location, e.number
                    FROM EggsLaying AS e
                    WHERE e.id = :id
                """, EggsLayingMeasureDTO.class).setParameter("id", measure.getId());
                return query.getSingleResult();
            }

            default -> throw new IllegalStateException("");
        }
    }


}
