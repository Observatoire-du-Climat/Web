package ch.heigvd.dto;

import ch.heigvd.entity.MeasureType;

import java.time.LocalDate;

public record BirdMigrationMeasureDTO(Long id, LocalDate date, String location, MeasureType type, String birdType, Boolean arrival) {
}
