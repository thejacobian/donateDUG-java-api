package donateDugJavaPkg;

//import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called postRepository
// CRUD refers Create, Read, Update, Delete
//@JsonTypeName("users")
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
