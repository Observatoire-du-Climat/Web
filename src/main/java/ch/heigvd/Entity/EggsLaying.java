package ch.heigvd.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

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
