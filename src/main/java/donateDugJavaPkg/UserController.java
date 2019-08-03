package donateDugJavaPkg;

//import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import com.fasterxml.jackson.annotation.JsonTypeName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

@RestController
//@JsonTypeName("users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlotRepository plotRepository;

    @Autowired
    private GardenRepository gardenRepository;

    @Autowired
    private UserService userService;

    //at the top of the class definition near userRepository and userService:
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // INDEX Route
    @GetMapping("/user")
    public Iterable<User> getUsers(){
        System.out.println("-----------------------------------------------");
        System.out.println("***GET from /user Index Route is activated!***");
        System.out.println("-----------------------------------------------");
        return userRepository.findAll();
    }

    // SHOW Route
    @GetMapping("/user/{id}")
    public User showUser(@PathVariable("id") Long id, HttpSession session) throws Exception {
        System.out.println("-----------------------------------------------");
        System.out.println("***GET from /user/id Show Route is activated!***");
        System.out.println("-----------------------------------------------");
        try {
            User userSession = userRepository.findByUsername(session.getAttribute("username").toString());
            if (userSession == null) {
                throw new Exception("You must be logged in to view users");
            }
            Optional<User> response = userRepository.findById(id);
            if (response.isPresent()) {
                return response.get();
            }
            throw new Exception("No such user");
            //return "showUser: Jake";
        } catch (Exception e) {
            System.out.println("Error when validating session user access to user data, please login again, error: " + e);
            return null;
        }
    }

//    // SHOW Route
//    @GetMapping("/user/{id}")
//    public User showUser(@PathVariable("id") Long id, HttpSession session) throws Exception {
//        System.out.println("-----------------------------------------------");
//        System.out.println("***GET from /user/id Show Route is activated!***");
//        System.out.println("-----------------------------------------------");
//        try {
//            User userSession = userRepository.findByUsername(session.getAttribute("username").toString());
//            if (userSession == null) {
//                throw new Exception("You must be logged in to view users");
//            }
//            Optional<User> response = userRepository.findById(id);
//            if (response.isPresent()) {
//                User userPath = response.get();
//                if (id == userSession.getId()) {
//                    return response.get();
//                } else {
//                    System.out.println("Unable to show user details for this user");
//                    return null;
//                }
//            }
//            throw new Exception("No such user");
//            //return "showUser: Jake";
//        } catch (Exception e) {
//            System.out.println("Error when validating session user access to user data, please login again, error: " + e);
//            return null;
//        }
//    }

    // SHOW PLOTS Route
    @GetMapping("/user/profile/plots")
//    @JsonTypeInfo(include=JsonTypeInfo.As.WRAPPER_OBJECT, use=JsonTypeInfo.Id.NAME)
    public Iterable<Plot> showPlots(HttpSession session) throws Exception {
        //public String showUser() {
        System.out.println("----------------------------------------------------------");
        System.out.println("***GET from /user/id/plots Index Route is activated!***");
        System.out.println("----------------------------------------------------------");
        try {
            User userSession = userRepository.findByUsername(session.getAttribute("username").toString());
            if (userSession == null) {
                throw new Exception("You must be logged in to view user plots");
            }
            Iterable<Plot> resPlots = plotRepository.findPlotsByUsers(userSession);
            int plotSize = 0;
            if (resPlots instanceof Collection) {
                plotSize = ((Collection<?>) resPlots).size();
            }
            if (plotSize > 0) {
                return resPlots;
            }
            throw new Exception("No plots for this user");
        } catch (Exception e) {
            System.out.println("Error viewing the user's plots: " + e);
            return null;
        }
    }

//    // SHOW PLOTS Route
//    @GetMapping("/user/{id}/plots")
//    public Iterable<Plot> showPlots(@PathVariable("id") Long id, HttpSession session) throws Exception {
//        //public String showUser() {
//        System.out.println("----------------------------------------------------------");
//        System.out.println("***GET from /user/id/plots Index Route is activated!***");
//        System.out.println("----------------------------------------------------------");
//        try {
//            User userSession = userRepository.findByUsername(session.getAttribute("username").toString());
//            if (userSession == null) {
//                throw new Exception("You must be logged in to view user plots");
//            }
//            Optional<User> resUser = userRepository.findById(id);
//            if (resUser.isPresent() && id == userSession.getId()) {
//                User userPath = resUser.get();
//                Iterable<Plot> resPlots = plotRepository.findPlotsByUsers(userPath);
//                int plotSize = 0;
//                if (resPlots instanceof Collection) {
//                    plotSize = ((Collection<?>) resPlots).size();
//                }
//                if (plotSize > 0) {
//                    return resPlots;
//                }
//                throw new Exception("No plots for this user");
//            }
//            throw new Exception("No such user or unable to show plots for this user");
//        } catch (Exception e) {
//            System.out.println("Error viewing the user's plots: " + e);
//            return null;
//        }
//    }

    // SHOW GARDENS Route
    @GetMapping("/user/profile/gardens")
    public Iterable<Garden> showGardens(HttpSession session) throws Exception {
        //public String showUser() {
        System.out.println("--------------------------------------------------------");
        System.out.println("***GET from /user/id/gardens Index Route is activated!***");
        System.out.println("--------------------------------------------------------");
        try {
            User userSession = userRepository.findByUsername(session.getAttribute("username").toString());
            if (userSession == null) {
                throw new Exception("You must be logged in to view user gardens");
            }
            Iterable<Garden> resGardens = gardenRepository.findGardensByUsers(userSession);
            int gardenSize = 0;
            if (resGardens instanceof Collection) {
                gardenSize = ((Collection<?>) resGardens).size();
            }
            if (gardenSize > 0) {
                return resGardens;
            }
            throw new Exception("No gardens for this user");
        } catch (Exception e) {
            System.out.println("Error viewing the user's gardens: " + e);
            return null;
        }
    }

//    // SHOW GARDENS Route
//    @GetMapping("/user/{id}/gardens")
//    public Iterable<Garden> showGardens(@PathVariable("id") Long id, HttpSession session) throws Exception {
//        //public String showUser() {
//        System.out.println("--------------------------------------------------------");
//        System.out.println("***GET from /user/id/gardens Index Route is activated!***");
//        System.out.println("--------------------------------------------------------");
//        try {
//            User userSession = userRepository.findByUsername(session.getAttribute("username").toString());
//            if (userSession == null) {
//                throw new Exception("You must be logged in to view user gardens");
//            }
//            Optional<User> resUser = userRepository.findById(id);
//            if (resUser.isPresent() && id == userSession.getId()) {
//                User userPath = resUser.get();
//                Iterable<Garden> resGardens = gardenRepository.findGardensByUsers(userPath);
//                int gardenSize = 0;
//                if (resGardens instanceof Collection) {
//                    gardenSize = ((Collection<?>) resGardens).size();
//                }
//                if (gardenSize > 0) {
//                    return resGardens;
//                }
//                throw new Exception("No gardens for this user");
//            }
//            throw new Exception("No such user or unable to show gardens for this user");
//        } catch (Exception e) {
//            System.out.println("Error viewing the user's gardens: " + e);
//            return null;
//        }
//    }

    // UPDATE Route
    @PutMapping("/user/{id}")
    public User updateUser(@RequestBody User formData, @PathVariable("id") long id, HttpSession session) throws Exception {
        System.out.println("--------------------------------------------------");
        System.out.println("***PUT from /user/id Update Route is activated!***");
        System.out.println("--------------------------------------------------");
        try {
            User userSession = userRepository.findByUsername(session.getAttribute("username").toString());
            if (userSession == null) {
                throw new Exception("You must be logged in to update user data");
            }
            Optional<User> resUser = userRepository.findById(id);
            if (resUser.isPresent() && id == userSession.getId()) {
                User userPath = resUser.get();
                userPath.setUsername(formData.getUsername());
                userPath.setEmail(formData.getEmail());
                userPath.setPassword(formData.getPassword());
                userPath.setZipcode(formData.getZipcode());
                userPath.setOrganization(formData.getOrganization());
                return userService.saveUser(userPath);
            }
            throw new Exception("No such user or unable to update data for this user");
            //return "updateUser: updatedJake";
        } catch (Exception e) {
            System.out.println("Error when updating the user's data: " + e);
            return null;
        }
    }

    // DELETE Route
    @DeleteMapping("/user/{id}")
    public String delete(@PathVariable("id") Long id, HttpSession session){
        System.out.println("-----------------------------------------------------");
        System.out.println("***DELETE from /user/id Delete Route is activated!***");
        System.out.println("-----------------------------------------------------");
        try {
            User userSession = userRepository.findByUsername(session.getAttribute("username").toString());
            if (userSession == null) {
                throw new Exception("You must be logged in to delete your account");
            }
            Optional<User> resUser = userRepository.findById(id);
            if (resUser.isPresent() && id == userSession.getId()) {
                User userPath = resUser.get();

                // remove the user from the plots attended that they may have had
                Iterable<Plot> resPlots = plotRepository.findPlotsByUsers(userPath);
                for (Plot resPlot : resPlots) {
                    if (resPlot.getUsers().size() == 1) {
                        plotRepository.delete(resPlot);
                        System.out.println("Deleted a plot that had id: " + id);
                    } else {
                        resPlot.getUsers().remove(userPath);
                        plotRepository.save(resPlot);
                        System.out.println("Deleted a user from a plot that had user id: " + id);
                    }
                }

                // remove the user from the gardened for Artists that they may have had
                Iterable<Garden> resGardens = gardenRepository.findGardensByUsers(userPath);
                for (Garden resGarden : resGardens) {
                    if (resGarden.getUsers().size() == 1) {
                        gardenRepository.delete(resGarden);
                        System.out.println("Deleted a garden that had id: " + id);
                    } else {
                        resGarden.getUsers().remove(userPath);
                        gardenRepository.save(resGarden);
                        System.out.println("Deleted a user from a garden that had user id: " + id);
                    }
                }

                // delete the user
                userRepository.deleteById(id);
                return "Deleted a user and all associated data that had id: " + id;
            }
            throw new Exception("No such user or unable to update data for this user");
            //return "deletedUser: deletedJake";
        } catch (Exception e) {
            System.out.println("Error when deleting the user: " + e);
            return null;
        }
    }

    // CREATE/REGISTER Route
    @PostMapping("/auth/register")
    public User createUser(@RequestBody User user, HttpSession session){
        System.out.println("---------------------------------------------------------");
        System.out.println("***POST from /user Create/Register Route is activated!***");
        System.out.println("---------------------------------------------------------");
        try {
            User newUser = userService.saveUser(user);
            if (newUser != null) {
                session.setAttribute("username", newUser.getUsername());
            }
            return newUser;
        } catch (Exception e) {
            System.out.println("Unable to create new user with error: " + e);
            return  null;
        }
    }

    // LOGIN route
    @PostMapping("/auth/login")
    public User loginUser(@RequestBody User login, HttpSession session) throws IOException {
        System.out.println("-----------------------------------------------");
        System.out.println("***POST from /user Login Route is activated!***");
        System.out.println("-----------------------------------------------");
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        try {
            User user = userRepository.findByUsername(login.getUsername());
            if (user == null) {
                throw new IOException("Invalid Credentials");
            }
            boolean valid = bCryptPasswordEncoder.matches(login.getPassword(), user.getPassword());
            if (valid) {
                session.setAttribute("username", user.getUsername());
                return user;
            } else {
                throw new IOException("Invalid Credentials");
            }
        } catch (Exception e) {
            System.out.println("Unable to login with error: " + e);
            return null;
        }
    }

    // LOGOUT route
    @PostMapping("/auth/logout")
    public boolean logout(HttpSession session) {
        System.out.println("------------------------------------------------");
        System.out.println("***POST from /user Logout Route is activated!***");
        System.out.println("------------------------------------------------");
        if(session !=null) {
            try {
                session.removeAttribute("username");
                session.invalidate();
                return true;
            } catch (Exception e) {
                System.out.println("Unable to invalidate session with Error: " + e);
                return false;
            }
        } else {
            // session already null/expired/invalidated so return true
            return true;
        }
    }
}
