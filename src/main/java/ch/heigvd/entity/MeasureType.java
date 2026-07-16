package ch.heigvd.entity;

/**
 * Enumeration of all measurement type supported by the application.
 * Each measure type is associated with a French readable label.
 */
public enum MeasureType {
    TEMPERATURE("Température"),
    SNOW_HEIGHT("Hauteur des neiges"),
    BIRD_MIGRATION("Migration des oiseaux"),
    EGGS_LAYING("Relevé des pontes");

    private final String label;

    MeasureType(String label) {
        this.label = label;
    }

    /**
     * Get the displayed label associated with this measurement type.
     * @return the label
     */
    public String getLabel() {
        return label;
    }
}
