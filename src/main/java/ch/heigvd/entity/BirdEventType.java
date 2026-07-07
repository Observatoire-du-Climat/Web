package ch.heigvd.entity;

public enum BirdEventType {
    ARRIVAL("Arrivée"),
    DEPARTURE("Départ");

    private final String label;

    BirdEventType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
