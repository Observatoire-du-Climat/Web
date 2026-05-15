package ch.heigvd.Entity;

import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "users") //name "user" not avaiable
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    @Password
    private String password;

    @Roles
    private String role;

    @Column(name = "is_admin", nullable = false)
    private Boolean isAdmin;

    @Column(name = "is_valid", nullable = false)
    private Boolean isValid;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Measure> measures;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserParameters parameters;

    public User() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public Set<Measure> getMeasures() {
        return measures;
    }

    public void setMeasures(Set<Measure> measures) {
        this.measures = measures;
    }

    public UserParameters getParameters() {
        return parameters;
    }

    public void setParameters(UserParameters parameters) {
        this.parameters = parameters;
    }
}
