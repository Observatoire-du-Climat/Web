package ch.heigvd.dto;

import ch.heigvd.entity.MeasureType;
import ch.heigvd.entity.WeatherType;

import java.time.LocalDate;

//author will be used only in the admin interface
public record SnowHeightMeasureDTO(Long id, LocalDate date, String location, MeasureType type, String author, Integer height, WeatherType weather, Integer precipitation) {
}
