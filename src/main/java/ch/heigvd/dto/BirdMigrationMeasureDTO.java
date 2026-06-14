package ch.heigvd.dto;

import ch.heigvd.entity.BirdEventType;
import ch.heigvd.entity.BirdSpecies;
import ch.heigvd.entity.MeasureType;

import java.time.LocalDate;

public record BirdMigrationMeasureDTO(Long id, LocalDate date, String location, MeasureType type, BirdSpecies specie, BirdEventType event) {
}
