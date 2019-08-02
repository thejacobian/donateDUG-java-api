package donateDugJavaPkg;

//import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "plot")
//@JsonTypeName("plots")
public class Plot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH}) //CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST})
    @JoinTable(name = "plot_has_user",
            joinColumns = @JoinColumn(name = "plot_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> users = new HashSet<>();

    @Column (name = "plantId", unique = true, length = 20, nullable = false)
    private String plantId;

    @Column (name = "plotName")
    private String plotName;

    @Column (name = "width")
    private Float width;

    @Column (name = "height")
    private Float height;

    @Column (name = "plant", length = 2000)
    private String plant;
    //private Set<String> set = new HashSet<>();

    public String getPlotName() {
        return plotName;
    }

    public void setPlotName(String plotName) {
        this.plotName = plotName;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getWidth() {
        return width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getPlantId() {
        return plantId;
    }

    public void setPlantId(String plantId) {
        this.plantId = plantId;
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