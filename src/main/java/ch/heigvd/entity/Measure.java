package ch.heigvd.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents the Measure Entity
 * This is a superclass for inheritance of measure subclasses. It defines common fields of all measure types.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "measure")
public class Measure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "measure_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "location", nullable = false)
    private String location;

    @Enumerated(EnumType.STRING)
    private MeasureType type;

    @OneToMany(mappedBy = "measure", cascade = CascadeType.ALL)
    private Set<MeasurePicture> pictures = new HashSet<>();

    public Measure() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public MeasureType getType() {
        return type;
    }

    public void setType(MeasureType type) {
        this.type = type;
    }

    public Set<MeasurePicture> getPictures() {
        return pictures;
    }

    public void setPictures(Set<MeasurePicture> pictures) {
        this.pictures = pictures;
    }
}