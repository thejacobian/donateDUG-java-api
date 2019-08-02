package donateDugJavaPkg;

//import com.fasterxml.jackson.annotation.JsonTypeName;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called postRepository
// CRUD refers Create, Read, Update, Delete
//@JsonTypeName("plots")
public interface PlotRepository extends CrudRepository<Plot, Long> {
    Iterable<Plot> findPlotsByUsers(User user);
    Optional<Plot> findByPlotName(String plotName);
    //Iterable<Plot> findPlotsByGarden(Garden garden);
}
