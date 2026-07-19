package ch.heigvd.entity;

/**
 * Enumeration of all the events, arrival or departure, supported by the application.
 * It is used in BirdMigration measurement.
 * Each measure type is associated with a French readable label.
 */
public enum BirdEventType {
    ARRIVAL("Arrivée"),
    DEPARTURE("Départ");

    private final String label;

    BirdEventType(String label) {
        this.label = label;
    }

    /**
     * Get the displayed label associated with this event type.
     * @return the label
     */
    public String getLabel() {
        return label;
    }
}
