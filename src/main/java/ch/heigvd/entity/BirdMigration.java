package ch.heigvd.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "bird_migration")
public class BirdMigration extends Measure {

    @Column(name = "bird_type", length = 15, nullable = false)
    private String birdType;

    @Column(name = "arrival", nullable = false)
    private Boolean arrival;

    public BirdMigration() {}

    public String getBirdType() {
        return birdType;
    }

    public void setBirdType(String birdType) {
        this.birdType = birdType;
    }

    public Boolean getArrival() {
        return arrival;
    }

    public void setArrival(Boolean arrival) {
        this.arrival = arrival;
    }
}
