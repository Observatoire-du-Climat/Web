package ch.heigvd.Entity;

import jakarta.persistence.*;

@Entity
public class UserParameters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parameters_id")
    private Long id;

    @Column(name = "notifications")
    private boolean notifications;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    public User user;

    public UserParameters() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isNotifications() {
        return notifications;
    }

    public void setNotifications(boolean notifications) {
        this.notifications = notifications;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
