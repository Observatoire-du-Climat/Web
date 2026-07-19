package ch.heigvd.entity;

/**
 * Enumeration of all different weather conditions supported by the application.
 * It is used in SnowHeight measurement.
 * Each measure type is associated with a French readable label.
 */
public enum WeatherType {
    SUNNY("Soleil"),
    CLOUDY("Nuageux"),
    RAINY("Pluvieux"),
    SNOWY("Neigeux"),
    WINDY("Venteux");

    private final String label;

    WeatherType(String label) {
        this.label = label;
    }

    /**
     * Get the displayed label associated with this weather condition.
     * @return the label
     */
    public String getLabel() {
        return label;
    }
}
