package ch.heigvd.entity;

import jakarta.persistence.*;

/**
 * This class represents a MeasurePicture entity.
 * It contains the path from where a picture of a specified measure is stored.
 */
@Entity
public class MeasurePicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picture_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "measure_id", nullable = false)
    private Measure measure;

    @Column(name = "path", nullable = false)
    private String path;

    public MeasurePicture() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
