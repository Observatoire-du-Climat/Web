package ch.heigvd.entity;

public enum MeasureType {
    TEMPERATURE("Température"),
    SNOW_HEIGHT("Hauteur des neiges"),
    BIRD_MIGRATION("Migration des oiseaux"),
    EGGS_LAYING("Relevé des pontes");

    private final String label;

    MeasureType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
