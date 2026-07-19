package ch.heigvd.dto;

import ch.heigvd.entity.BirdEventType;
import ch.heigvd.entity.BirdSpecies;
import ch.heigvd.entity.MeasureType;

import java.time.LocalDate;

/**
 * Data Transfer Object representing a BirdMigration measure, showing only displayable value.
 * @param id the id of the measure.
 * @param date the date of the measure.
 * @param location the location where the measure was taken.
 * @param type the type of the measure.
 * @param author the name of the user that created the measure (only used in administration page).
 * @param specie the observed specie.
 * @param event the observed event.
 */
public record BirdMigrationMeasureDTO(Long id, LocalDate date, String location, MeasureType type, String author, BirdSpecies specie, BirdEventType event) {
}
