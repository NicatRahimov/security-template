package az.rahimov.securitytemplate.controller;

import az.rahimov.securitytemplate.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("secured")
public class UserController {

    @GetMapping("hello")
    private String hello(){
        return "Hello";
    }


}
