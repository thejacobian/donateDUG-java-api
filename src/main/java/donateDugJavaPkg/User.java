package donateDugJavaPkg;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    private Set<Plot> plots = new HashSet<>();

    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    private Set<Garden> gardens = new HashSet<>();

    @Column (name = "username", unique = true, length = 20, nullable = false)
    private String username;

    @Column (name = "password", length = 100, nullable = false)
    private String password;

    @Column (name = "location")
    private String location;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<Plot> getPlot() {
        return plots;
    }

    public void setPlot(Set<Plot> plots) {
        this.plots = plots;
    }

    public Set<Garden> getGardens() {
        return gardens;
    }

    public void setGardens(Set<Garden> gardens) {
        this.gardens = gardens;
    }

    public Long getId() {
        return id;
    }
}
