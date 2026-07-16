package ch.heigvd.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

/**
 * This class represents a EggsLaying entity.
 * It is a subclass of the Measure entity.
 */
@Entity
public class EggsLaying extends Measure{

    @Column(name = "number", nullable = false)
    private Integer number;

    public EggsLaying() {}

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
