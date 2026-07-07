package ch.heigvd.entity;

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

    public String getLabel() {
        return label;
    }
}
