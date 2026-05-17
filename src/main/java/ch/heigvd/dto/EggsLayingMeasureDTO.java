package ch.heigvd.dto;

import java.time.LocalDate;

public record EggsLayingMeasureDTO(Long id, LocalDate date, String location, String type, Integer number) {
}
