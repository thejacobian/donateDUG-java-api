package donateDugJavaPkg;

//import com.fasterxml.jackson.annotation.JsonTypeName;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called postRepository
// CRUD refers Create, Read, Update, Delete
//@JsonTypeName("gardens")
public interface GardenRepository extends CrudRepository<Garden, Long> {
    Iterable<Garden> findGardensByUsers(User user);
    Optional<Garden> findByGardenName(String gardenName);
    //Iterable<Garden> findGardensByPlots(Plot plot);
}
