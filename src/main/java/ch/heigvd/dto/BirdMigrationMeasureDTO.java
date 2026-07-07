package ch.heigvd.dto;

import ch.heigvd.entity.BirdEventType;
import ch.heigvd.entity.BirdSpecies;
import ch.heigvd.entity.MeasureType;

import java.time.LocalDate;

//author will be used only in the admin interface
public record BirdMigrationMeasureDTO(Long id, LocalDate date, String location, MeasureType type, String author, BirdSpecies specie, BirdEventType event) {
}
