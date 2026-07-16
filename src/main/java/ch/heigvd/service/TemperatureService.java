package ch.heigvd.service;

import ch.heigvd.dto.TemperatureMeasureDTO;
import ch.heigvd.entity.MeasureType;
import ch.heigvd.entity.Temperature;
import ch.heigvd.entity.User;
import io.quarkus.security.UnauthorizedException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service in charge of the Temperature Class.
 * It contains the business logic associated with the Temperature Entity.
 */
@ApplicationScoped
public class TemperatureService {

    private final EntityManager em;

    @Inject
    public TemperatureService(EntityManager em) {
        this.em = em;
    }

    /**
     * Insert a new Temperature Measure
     * @param userId the id of the user that created the measure
     * @param date the date of the measure
     * @param location the location of the measure
     * @param degree the degree value
     * @return A DTO of the newly created Temperature if the insert was successful
     * @throws NotFoundException if no user possess this userId
     * @throws ForbiddenException if the user does not have the rights to create a Measure
     */
    @Transactional
    public TemperatureMeasureDTO addTemperature(Long userId, LocalDate date, String location, Integer degree) {

        Temperature temperature = new Temperature();
        temperature.setDate(date);
        temperature.setLocation(location);
        temperature.setDegree(degree);
        temperature.setType(MeasureType.TEMPERATURE);

        User user = em.find(User.class, userId);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        if (!user.getValid()) {
            throw new ForbiddenException("User not valid");
        }
        temperature.setUser(user);
        user.getMeasures().add(temperature);

        em.persist(temperature);
        return new TemperatureMeasureDTO(temperature.getId(),
                                        temperature.getDate(),
                                        temperature.getLocation(),
                                        temperature.getType(),
                                        temperature.getUser().getName(),
                                        temperature.getDegree());
    }

    /**
     * Update a Temperature Measure
     * All the attribute are updated, even if not all of them are changed
     * @param measureId the id of the Temperature Measure to Update
     * @param date the (new) date of the Temperature
     * @param location the (new) location of the Temperature
     * @param degree the (new) degree of the Temperature
     * @return a DTO with the modified Temperature if the update was successful
     * @throws NotFoundException if no measure possess this measureId
     */
    @Transactional
    public TemperatureMeasureDTO modifyTemperatureById(Long measureId, LocalDate date, String location, Integer degree) {

        Temperature temperatureToModify = em.find(Temperature.class, measureId);
        if (temperatureToModify == null) {
            throw new NotFoundException("Measure not found");
        }
        temperatureToModify.setDate(date);
        temperatureToModify.setLocation(location);
        temperatureToModify.setDegree(degree);
        return new TemperatureMeasureDTO(temperatureToModify.getId(),
                                        temperatureToModify.getDate(),
                                        temperatureToModify.getLocation(),
                                        temperatureToModify.getType(),
                                        temperatureToModify.getUser().getName(),
                                        temperatureToModify.getDegree());

    }

    /**
     * Get all the existing Temperature measures in details.
     * @return a list of DTO from all the Temperature measures.
     */
    public List<TemperatureMeasureDTO> getAllTemperatureMeasures() {
        var query = em.createQuery("""
                    SELECT new ch.heigvd.dto.TemperatureMeasureDTO(t.id, t.date, t.location, t.type, t.user.name, t.degree)
                    FROM Temperature AS t
                """, TemperatureMeasureDTO.class);

        return query.getResultList();
    }

}
