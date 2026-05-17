package ch.heigvd.dto;

import java.time.LocalDate;

public record MeasureDTO(Long id, LocalDate date, String location, String type) {
}
