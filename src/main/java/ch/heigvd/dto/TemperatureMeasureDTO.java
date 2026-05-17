package ch.heigvd.dto;

import java.time.LocalDate;

public record TemperatureMeasureDTO(Long id, LocalDate date, String location, String type, Integer degree) {
}
