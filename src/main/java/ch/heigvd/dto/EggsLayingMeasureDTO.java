package ch.heigvd.dto;

import ch.heigvd.entity.MeasureType;

import java.time.LocalDate;

/**
 * Data Transfer Object representing an EggsLaying measure, showing only displayable value.
 * @param id the id of the measure.
 * @param date the date of the measure.
 * @param location the location where the measure was taken.
 * @param type the type of the measure.
 * @param author the name of the user that created the measure (only used in administration page).
 * @param number the measured number of eggs.
 */
public record EggsLayingMeasureDTO(Long id, LocalDate date, String location, MeasureType type, String author, Integer number) {
}
