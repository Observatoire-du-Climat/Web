package ch.heigvd.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

/**
 * This class represents a SnowHeight entity.
 * It is a subclass of the Measure entity.
 */
@Entity
public class SnowHeight extends Measure{

    @Column(name = "height", nullable = false)
    private Integer height;

    @Column(name = "weather", nullable = false)
    @Enumerated(EnumType.STRING)
    private WeatherType weather;

    @Column(name = "precipitation", nullable = false)
    private Integer precipitation;

    public SnowHeight() {}

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public WeatherType getWeather() {
        return weather;
    }

    public void setWeather(WeatherType weather) {
        this.weather = weather;
    }

    public Integer getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(Integer precipitation) {
        this.precipitation = precipitation;
    }
}
