package ch.heigvd.dto;

import java.time.LocalDate;

public record BirdMigrationMeasureDTO(Long id, LocalDate date, String location, String type, String birdType, Boolean arrival) {
}
