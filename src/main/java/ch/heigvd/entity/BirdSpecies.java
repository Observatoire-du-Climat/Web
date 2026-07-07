package ch.heigvd.entity;

public enum BirdSpecies {
    SWALLOW("Hirondelle"),
    SWIFT("Martinet");

    private final String label;

    BirdSpecies(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
