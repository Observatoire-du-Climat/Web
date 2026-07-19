package ch.heigvd.entity;

/**
 * Enumeration of all different bird species supported by the application.
 * It is used in BirdMigration measurement.
 * Each measure type is associated with a French readable label.
 */
public enum BirdSpecies {
    SWALLOW("Hirondelle"),
    SWIFT("Martinet");

    private final String label;

    BirdSpecies(String label) {
        this.label = label;
    }

    /**
     * Get the displayed label associated with this specie.
     * @return the label
     */
    public String getLabel() {
        return label;
    }
}
