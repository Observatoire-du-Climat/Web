package ch.heigvd.service;

import ch.heigvd.dto.SnowHeightMeasureDTO;
import ch.heigvd.entity.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDate;

@ApplicationScoped
public class SnowHeightService {

    private final EntityManager em;

    @Inject
    public SnowHeightService(EntityManager em) {
        this.em = em;
    }

    /**
     * Insert a new SnowHeight Measure
     * @param userId the id of the user that created the measure
     * @param date the date of the measure
     * @param location the location of the measure
     * @param height the height value of the measure
     * @param weather the weather value of the measure
     * @param precipitation the precipitation value of the measure
     * @return a DTO with the newly created SnowHeight if the insert was successful
     */
    @Transactional
    public SnowHeightMeasureDTO addSnowHeight(Long userId, LocalDate date, String location, Integer height, String weather, Integer precipitation) {

        SnowHeight snowHeight = new SnowHeight();
        snowHeight.setDate(date);
        snowHeight.setLocation(location);
        snowHeight.setHeight(height);
        snowHeight.setWeather(WeatherType.valueOf(weather.toUpperCase()));
        snowHeight.setPrecipitation(precipitation);
        snowHeight.setType(MeasureType.SNOW_HEIGHT);

        User user = em.find(User.class, userId);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        if (!user.getValid()) {
            throw new ForbiddenException("User not valid");
        }
        snowHeight.setUser(user);
        user.getMeasures().add(snowHeight);

        em.persist(snowHeight);
        return new SnowHeightMeasureDTO(snowHeight.getId(),
                snowHeight.getDate(),
                snowHeight.getLocation(),
                snowHeight.getType(),
                snowHeight.getUser().getName(),
                snowHeight.getHeight(),
                snowHeight.getWeather(),
                snowHeight.getPrecipitation());
    }

    /**
     * Update a SnowHeight Measure
     * All the attribute are updated, even if not all of them are changed
     * @param measureId the id of the measure to update
     * @param date the (new) date of the measure
     * @param location the (new) location of the measure
     * @param height the (new) height value of the measure
     * @param weather the (new) weather value of the measure
     * @param precipitation the (new) precipitation value of the measure
     * @return a DTO with the modified SnowHeight if the update was successful
     */
    @Transactional
    public SnowHeightMeasureDTO modifySnowHeightById(Long measureId, LocalDate date, String location, Integer height, String weather, Integer precipitation) {

        SnowHeight snowHeightToModify = em.find(SnowHeight.class, measureId);
        if (snowHeightToModify == null) {
            throw new NotFoundException("Measure not found");
        }
        snowHeightToModify.setDate(date);
        snowHeightToModify.setLocation(location);
        snowHeightToModify.setHeight(height);
        snowHeightToModify.setWeather(WeatherType.valueOf(weather.toUpperCase()));
        snowHeightToModify.setPrecipitation(precipitation);
        return new SnowHeightMeasureDTO(snowHeightToModify.getId(),
                snowHeightToModify.getDate(),
                snowHeightToModify.getLocation(),
                snowHeightToModify.getType(),
                snowHeightToModify.getUser().getName(),
                snowHeightToModify.getHeight(),
                snowHeightToModify.getWeather(),
                snowHeightToModify.getPrecipitation());

    }
}
