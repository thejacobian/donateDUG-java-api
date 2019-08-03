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

    @Column (name = "email", unique = true, length = 40, nullable = false)
    private String email;

    @Column (name = "password", length = 100, nullable = false)
    private String password;

    @Column (name = "zipcode", length = 5, nullable = false)
    private String zipcode;

    @Column (name = "organization", length = 40, nullable = true)
    private String organization;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
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
