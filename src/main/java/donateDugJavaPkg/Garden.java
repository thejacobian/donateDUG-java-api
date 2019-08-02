package donateDugJavaPkg;

//import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "garden")
//@JsonTypeName("gardens")
public class Garden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    @JoinTable(name = "garden_has_user",
        joinColumns = @JoinColumn(name = "garden_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> users = new HashSet<>();

    @Column (name = "gardenName")
    private String gardenName;

    @Column (name = "city")
    private String city;

    @Column (name = "state")
    private String state;

    @Column (name = "latitude")
    private String latitude;

    @Column (name = "longitude")
    private String longitude;

    public String getGardenName() {
        return gardenName;
    }

    public void setGardenName(String gardenName) {
        this.gardenName = gardenName;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Long getId() {
        return id;
    }

}