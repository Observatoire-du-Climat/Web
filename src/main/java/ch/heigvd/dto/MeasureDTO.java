package ch.heigvd.dto;

import ch.heigvd.entity.MeasureType;

import java.time.LocalDate;

//author will be used only in the admin interface
public record MeasureDTO(Long id, LocalDate date, String location, MeasureType type, String author) {
}