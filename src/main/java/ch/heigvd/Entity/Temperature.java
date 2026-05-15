package ch.heigvd.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Temperature extends Measure {

    @Column(name = "degree", nullable = false)
    private Integer degree;

    public Temperature() {}

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }
}
