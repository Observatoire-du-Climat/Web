package ch.heigvd.dto;

import ch.heigvd.entity.MeasureType;
import ch.heigvd.entity.WeatherType;

import java.time.LocalDate;

/**
 * Data Transfer Object representing a SnowHeight measure, showing only displayable value.
 * @param id the id of the measure.
 * @param date the date of the measure.
 * @param location the location where the measure was taken.
 * @param type the type of the measure.
 * @param author the name of the user that created the measure (only used in administration page).
 * @param height the measured height of the snow in cm.
 * @param weather the weather condition of the day.
 * @param precipitation the measured precipitation of the day in cm.
 */
public record SnowHeightMeasureDTO(Long id, LocalDate date, String location, MeasureType type, String author, Integer height, WeatherType weather, Integer precipitation) {
}
