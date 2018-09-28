import com.assignment1.pojo.User;
import com.assignment1.repository.UserRepository;
import com.assignment1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com"})
@EnableJpaRepositories
@EntityScan("com")
public class Application implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        User user = userRepository.findByNameAndPassword("aaa", "123");
        if (user == null) {
            userService.addUser("aaa", "123", "1225 S Dorsey Ln");
        }

        user = userRepository.findByNameAndPassword("bbb", "123");
        if (user == null) {
            userService.addUser("bbb", "123", "1225 S Dorsey Ln");
        }

        user = userRepository.findByNameAndPassword("ccc", "123");
        if (user == null) {
            userService.addUser("ccc", "123", "1225 S Dorsey Ln");
        }
    }
}