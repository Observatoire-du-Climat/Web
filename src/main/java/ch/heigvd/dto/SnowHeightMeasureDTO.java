package ch.heigvd.dto;

import ch.heigvd.entity.MeasureType;
import ch.heigvd.entity.WeatherType;

import java.time.LocalDate;

public record SnowHeightMeasureDTO(Long id, LocalDate date, String location, MeasureType type, Integer height, WeatherType weather, Integer precipitation) {
}
