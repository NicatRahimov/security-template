package az.rahimov.securitytemplate.controller;

import az.rahimov.securitytemplate.model.User;
import az.rahimov.securitytemplate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @PostMapping("get")
    public List<User> getAllUsers(){
       return userRepository.findAll();
    }
    @PostMapping("post")
    public void addUser(@RequestBody User user){
        userRepository.save(user);
    }
}
