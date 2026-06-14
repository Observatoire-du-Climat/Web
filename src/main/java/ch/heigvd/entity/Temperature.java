package ch.heigvd.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "temperature")
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
