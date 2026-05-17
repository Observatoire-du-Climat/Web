package ch.heigvd.dto;

import java.time.LocalDate;

public record SnowHeightMeasureDTO(Long id, LocalDate date, String location, String type, Integer height, String weather, Integer precipitation) {
}
