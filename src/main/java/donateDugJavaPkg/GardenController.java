package donateDugJavaPkg;

//import com.fasterxml.jackson.annotation.JsonTypeName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/garden")
//@JsonTypeName("gardens")
public class GardenController {

    @Autowired
    private GardenRepository gardenRepository;

    @Autowired
    private UserRepository userRepository;

    // INDEX Route
    @GetMapping()
    public Iterable<Garden> getGardens(){
        System.out.println("-----------------------------------------------");
        System.out.println("***GET from /garden Index Route is activated!***");
        System.out.println("-----------------------------------------------");
        // return "getGardens: The Rolling Stones";
        return gardenRepository.findAll();
    }

    // SHOW Route
    @GetMapping("/{id}")
    public Garden showGarden(@PathVariable("id") Long id) throws Exception {
        System.out.println("-----------------------------------------------");
        System.out.println("***GET from /garden/id Show Route is activated!***");
        System.out.println("-----------------------------------------------");
        // return "showGarden: The Rolling Stones";
        Optional<Garden> response = gardenRepository.findById(id);
        if (response.isPresent()) {
            return response.get();
        }
        throw new Exception("No such garden");
    }

//    // SHOW Route
//    @GetMapping("/{id}")
//    public Garden showGarden(@PathVariable("id") Long id, HttpSession session) throws Exception {
//        System.out.println("-----------------------------------------------");
//        System.out.println("***GET from /garden/id Show Route is activated!***");
//        System.out.println("-----------------------------------------------");
//        try {
//            User user = userRepository.findByUsername(session.getAttribute("username").toString());
//            if (user == null) {
//                throw new Exception("You must be logged in to show the garden");
//            }
//            Optional<Garden> response = gardenRepository.findById(id);
//            if(response.isPresent()) {
//                Garden garden = response.get();
//                Set<User> curGardenUsers = garden.getUsers();
//                if (curGardenUsers.contains(user)) {
//                    return response.get();
//                } else {
//                    System.out.println("Unable to access the garden with the session user");
//                    return null;
//                }
//            }
//            throw new Exception("No such garden");
//            // return "showGarden: The Rolling Stones";
//        } catch (Exception e) {
//            System.out.println("Error when validating session user access to create garden, please login again, error: " + e);
//            return null;
//        }
//    }

    // CREATE Route
    @PostMapping()
    public Garden createGarden(@RequestBody Garden request, HttpSession session) throws Exception {
        System.out.println("----------------------------------------------------");
        System.out.println("***POST from /garden createGarden Route is activated!***");
        System.out.println("----------------------------------------------------");
        try {
            User user = userRepository.findByUsername(session.getAttribute("username").toString());
            if (user == null) {
                throw new Exception("You must be logged in to add the garden");
            }
            Optional<Garden> response = gardenRepository.findByGardenName(request.getGardenName());
            if(response.isPresent()) {
                Garden garden = response.get();
                if (garden.getUsers().add(user)) {
                    return gardenRepository.save(garden);
                } else {
                    System.out.println("Unable to add the existing garden to the session user");
                    return null;
                }
            } else {
                Set<User> curGardenUsers = request.getUsers();
                if (curGardenUsers.add(user)) {
                    return gardenRepository.save(request);
                } else {
                    System.out.println("Unable to add the new garden to the session user");
                    return null;
                }
            }
            // return "createGarden: The Rolling Stones";
        } catch (Exception e) {
            System.out.println("Error when validating session user access to create garden, please login again, error: " + e);
            return null;
        }
    }


    // UPDATE ROUTE
    @PutMapping("/{id}")
    public Garden updateGarden(@RequestBody Garden request, @PathVariable("id") Long id, HttpSession session) throws Exception {
        System.out.println("--------------------------------------------------");
        System.out.println("***PUT from /garden/id Route is activated!***");
        System.out.println("--------------------------------------------------");
        try {
            User user = userRepository.findByUsername(session.getAttribute("username").toString());
            if (user == null) {
                throw new Exception("You must be logged in to update the garden");
            }
            Optional<Garden> response = gardenRepository.findById(id);
            if(response.isPresent()) {
                Garden garden = response.get();
                Set<User> curGardenUsers = garden.getUsers();
                if (curGardenUsers.contains(user)) {
                    garden.setGardenName(request.getGardenName());
                    return gardenRepository.save(garden);
                } else {
                    System.out.println("The session user does not have access to this garden.");
                    return null;
                }
                // return "updateGarden: The Rolling Stones";
            }
            throw new Exception("No such garden");
        } catch (Exception e) {
            System.out.println("Error when validating session user access to garden, please login again, error: " + e);
            return null;
        }
    }

    // DELETE Route
    @DeleteMapping("/{id}")
    public Garden deleteGarden(@PathVariable("id") Long id, HttpSession session){
        System.out.println("-----------------------------------------------------");
        System.out.println("***DELETE from /garden/id Delete Route is activated!***");
        System.out.println("-----------------------------------------------------");
        try {
            User user = userRepository.findByUsername(session.getAttribute("username").toString());
            if (user == null) {
                throw new Exception("You must be logged in to delete the garden");
            }
            Optional<Garden> response = gardenRepository.findById(id);
            if(response.isPresent()) {
                Garden garden = response.get();
                Set<User> curGardenUsers = garden.getUsers();
                if (curGardenUsers.contains(user) && curGardenUsers.size() == 1) {
                    gardenRepository.deleteById(id);
                    System.out.println("Deleted a garden that had id: " + id);
                    return garden;
                } else if (curGardenUsers.size() > 1 && curGardenUsers.remove(user)) {
                    return gardenRepository.save(garden);
                } else {
                    System.out.println("The session user does not have access to delete this garden.");
                    return null;
                }
                // return "deletedGarden: The Rolling Stones";
            }
            throw new Exception("No such garden");
        } catch (Exception e) {
            System.out.println("Error when validating session user access to garden, please login again, error: " + e);
            return null;
        }

    }
}
