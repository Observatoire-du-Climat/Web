package ch.heigvd.dto;

import ch.heigvd.entity.MeasureType;

import java.time.LocalDate;

public record SnowHeightMeasureDTO(Long id, LocalDate date, String location, MeasureType type, Integer height, String weather, Integer precipitation) {
}
