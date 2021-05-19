package QuizController;

import Entity.User;
import Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UserController {

    @Autowired
    private UserRepository userRepository;

    public UserController() {

    }

    @PostMapping(path = "/api/register", consumes = "application/json", produces = "application/json")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }
}