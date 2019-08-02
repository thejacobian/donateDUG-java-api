package donateDugJavaPkg;

//import com.fasterxml.jackson.annotation.JsonTypeName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/plot")
//@JsonTypeName("plots")
public class PlotController {

    @Autowired
    private PlotRepository plotRepository;

    @Autowired
    private UserRepository userRepository;

    // INDEX Route
    @GetMapping()
    public Iterable<Plot> getPlots(){
        System.out.println("-----------------------------------------------");
        System.out.println("***GET from /plot Index Route is activated!***");
        System.out.println("-----------------------------------------------");
        //return "getPlots: The Beatles, Red Rocks, 1964";
        return plotRepository.findAll();
    }

    // SHOW Route
    @GetMapping("/{id}")
    public Plot showPlot(@PathVariable("id") Long id) throws Exception {
        System.out.println("-----------------------------------------------");
        System.out.println("***GET from /plot/id Show Route is activated!***");
        System.out.println("-----------------------------------------------");
        //return "showPlot: The Beatles, Red Rocks, 1964";
        Optional<Plot> response = plotRepository.findById(id);
        if (response.isPresent()) {
            return response.get();
        }
        throw new Exception("No such plot");
    }

    // CREATE Route
    @PostMapping()
    public Plot createPlot(@RequestBody Plot request, HttpSession session) throws Exception {
        System.out.println("----------------------------------------------------");
        System.out.println("***POST from /plot createPlot Route is activated!***");
        System.out.println("----------------------------------------------------");
        try {
            User user = userRepository.findByUsername(session.getAttribute("username").toString());
            //Garden garden = gardenRepository.findByGardenName(session.getAttribute("gardenName").toString());
            if (user == null) {
                throw new Exception("You must be logged in to add the plot");
            }
            Optional<Plot> response = plotRepository.findByPlotName(request.getPlotName());
            if(response.isPresent()) {
                Plot plot = response.get();
                if (plot.getUsers().add(user)) {
                    return plotRepository.save(plot);
                } else {
                    System.out.println("Unable to add the existing plot to the session user");
                    return null;
                }
            } else {
                Set<User> curPlotUsers = request.getUsers();
                if (curPlotUsers.add(user)) {
                    return plotRepository.save(request);
                } else {
                    System.out.println("Unable to add the new plot to the session user");
                    return null;
                }
            }
            // return "createPlot: The Beatles, Red Rocks, 1964";
        } catch (Exception e) {
            System.out.println("Error when validating session user access to create plot, please login again, error: " + e);
            return null;
        }
    }


    // UPDATE ROUTE
    @PutMapping("/{id}")
    public Plot updatePlot(@RequestBody Plot request, @PathVariable("id") Long id, HttpSession session) throws Exception {
        System.out.println("--------------------------------------------------");
        System.out.println("***PUT from /plot/id Route is activated!***");
        System.out.println("--------------------------------------------------");
        try {
            User user = userRepository.findByUsername(session.getAttribute("username").toString());
            if (user == null) {
                throw new Exception("You must be logged in to update the plot");
            }
            Optional<Plot> response = plotRepository.findById(id);
            if(response.isPresent()) {
                Plot plot = response.get();
                Set<User> curPlotUsers = plot.getUsers();
                if (curPlotUsers.contains(user)) {
                    plot.setPlotName(request.getPlotName());
                    plot.setWidth(request.getWidth());
                    plot.setHeight(request.getHeight());
                    plot.setPlant(request.getPlant());
                    return plotRepository.save(plot);
                } else {
                    System.out.println("The session user does not have access to this plot.");
                    return null;
                }
                // return "updatePlot: The Beatles, Red Rocks, 1964";
            }
            throw new Exception("No such plot");
        } catch (Exception e) {
            System.out.println("Error when validating session user access to plot, please login again, error: " + e);
            return null;
        }
    }

    // DELETE Route
    @DeleteMapping("/{id}")
    public Plot deletePlot(@PathVariable("id") Long id, HttpSession session){
        System.out.println("--------------------------------------------------------");
        System.out.println("***DELETE from /plot/id Delete Route is activated!***");
        System.out.println("--------------------------------------------------------");
        try {
            User user = userRepository.findByUsername(session.getAttribute("username").toString());
            if (user == null) {
                throw new Exception("You must be logged in to delete the plot");
            }
            Optional<Plot> response = plotRepository.findById(id);
            if(response.isPresent()) {
                Plot plot = response.get();
                Set<User> curPlotUsers = plot.getUsers();
                if (curPlotUsers.contains(user) && curPlotUsers.size() == 1) {
                    plotRepository.deleteById(id);
                    System.out.println("Deleted a plot that had id: " + id);
                    return plot;
                } else if (curPlotUsers.size() > 1 && curPlotUsers.remove(user)) {
                    System.out.println("Deleted a plot that had id: " + id);
                    return plotRepository.save(plot);
                } else {
                    System.out.println("The session user does not have access to delete this plot.");
                    return null;
                }
                // return "deletePlot: The Beatles, Red Rocks, 1964";
            }
            throw new Exception("No such plot");
        } catch (Exception e) {
            System.out.println("Error when validating session user access to plot, please login again, error: " + e);
            return null;
        }

    }
}
