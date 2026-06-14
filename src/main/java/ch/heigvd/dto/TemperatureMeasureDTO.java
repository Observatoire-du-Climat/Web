package ch.heigvd.dto;

import ch.heigvd.entity.MeasureType;

import java.time.LocalDate;

public record TemperatureMeasureDTO(Long id, LocalDate date, String location, MeasureType type, Integer degree) {
}
