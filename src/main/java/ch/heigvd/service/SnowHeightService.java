package ch.heigvd.service;

import ch.heigvd.dto.SnowHeightMeasureDTO;
import ch.heigvd.entity.MeasureType;
import ch.heigvd.entity.SnowHeight;
import ch.heigvd.entity.Temperature;
import ch.heigvd.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.Optional;

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
     * @return an Optional with the newly created SnowHeight if the insert was successful, or an empty Optional if insertion
     */
    public Optional<SnowHeightMeasureDTO> addSnowHeight(Long userId, LocalDate date, String location, Integer height, String weather, Integer precipitation) {

        try {
            SnowHeight snowHeight = new SnowHeight();
            snowHeight.setDate(date);
            snowHeight.setLocation(location);
            snowHeight.setHeight(height);
            snowHeight.setWeather(weather);
            snowHeight.setPrecipitation(precipitation);
            snowHeight.setType(MeasureType.SNOW_HEIGHT);

            User user = em.find(User.class, userId);
            snowHeight.setUser(user);
            user.getMeasures().add(snowHeight);

            em.persist(snowHeight);
            return Optional.of(new SnowHeightMeasureDTO(snowHeight.getId(),
                    snowHeight.getDate(),
                    snowHeight.getLocation(),
                    snowHeight.getType().toString(),
                    snowHeight.getHeight(),
                    snowHeight.getWeather(),
                    snowHeight.getPrecipitation()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Update a SnowHeight Measure
     * All the attribute are updated, even if not all of them are changed
     * @param snowHeight the measure to update
     * @param date the (new) date of the measure
     * @param location the (new) location of the measure
     * @param height the (new) height value of the measure
     * @param weather the (new) weather value of the measure
     * @param precipitation the (new) precipitation value of the measure
     * @return an Optional with the modified SnowHeight if the update was successful, or an empty Optional if update failed
     */
    public Optional<SnowHeightMeasureDTO> modifySnowHeight(SnowHeight snowHeight, LocalDate date, String location, Integer height, String weather, Integer precipitation) {

        try {

            SnowHeight snowHeightToModify = em.find(SnowHeight.class, snowHeight.getId());
            snowHeightToModify.setDate(date);
            snowHeightToModify.setLocation(location);
            snowHeightToModify.setHeight(height);
            snowHeightToModify.setWeather(weather);
            snowHeightToModify.setPrecipitation(precipitation);
            return Optional.of(new SnowHeightMeasureDTO(snowHeightToModify.getId(),
                    snowHeightToModify.getDate(),
                    snowHeightToModify.getLocation(),
                    snowHeightToModify.getType().toString(),
                    snowHeightToModify.getHeight(),
                    snowHeightToModify.getWeather(),
                    snowHeightToModify.getPrecipitation()));

        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
