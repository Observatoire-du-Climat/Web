package ch.heigvd.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bird_migration")
public class BirdMigration extends Measure {

    @Column(name = "specie", nullable = false)
    @Enumerated(EnumType.STRING)
    private BirdSpecies specie;

    @Column(name = "event", nullable = false)
    @Enumerated(EnumType.STRING)
    private BirdEventType eventType;

    public BirdMigration() {}

    public BirdSpecies getSpecie() {
        return specie;
    }

    public void setSpecie(BirdSpecies specie) {
        this.specie = specie;
    }

    public BirdEventType getEventType() {
        return eventType;
    }

    public void setEventType(BirdEventType eventType) {
        this.eventType = eventType;
    }
}
