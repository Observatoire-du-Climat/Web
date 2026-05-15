package ch.heigvd.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class SnowHeight extends Measure{

    @Column(name = "height", nullable = false)
    private Integer height;

    @Column(name = "weather", nullable = false)
    private String weather;

    @Column(name = "precipitation", nullable = false)
    private Integer precipitation;

    public SnowHeight() {}

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public Integer getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(Integer precipitation) {
        this.precipitation = precipitation;
    }
}
