package ch.heigvd.service;

import ch.heigvd.entity.MeasureType;
import ch.heigvd.entity.Temperature;
import ch.heigvd.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.Optional;

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
     * @return an Optional with the newly created Temperature if the insert was successful, or an empty Optional if insertion failed
     */
    public Optional<MeasureService.TemperatureMeasureDTO> addTemperature(Long userId, LocalDate date, String location, Integer degree) {

        try {
            Temperature temperature = new Temperature();
            temperature.setDate(date);
            temperature.setLocation(location);
            temperature.setDegree(degree);
            temperature.setType(MeasureType.TEMPERATURE);

            User user = em.find(User.class, userId);
            temperature.setUser(user);
            user.getMeasures().add(temperature);

            em.persist(temperature);
            return Optional.of(new MeasureService.TemperatureMeasureDTO(temperature.getId(),
                                                                        temperature.getDate(),
                                                                        temperature.getLocation(),
                                                                        temperature.getType().toString(),
                                                                        temperature.getDegree()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Update a Temperature
     * All the attribute are updated, even if not all of them are changed
     * @param temperature the Temperature to Update
     * @param date the (new) date of the Temperature
     * @param location the (new) location of the Temperature
     * @param degree the (new) degree of the Temperature
     * @return an Optional with the modified Temperature if the update was successful, or an empty Optional if update failed
     */
    public Optional<MeasureService.TemperatureMeasureDTO> modifyTemperature(Temperature temperature, LocalDate date, String location, Integer degree) {

        try {

            Temperature temperatureToModify = em.find(Temperature.class, temperature.getId());
            temperatureToModify.setDate(date);
            temperatureToModify.setLocation(location);
            temperatureToModify.setDegree(degree);
            return Optional.of(new MeasureService.TemperatureMeasureDTO(temperatureToModify.getId(),
                                                                        temperatureToModify.getDate(),
                                                                        temperatureToModify.getLocation(),
                                                                        temperatureToModify.getType().toString(),
                                                                        temperatureToModify.getDegree()));

        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
