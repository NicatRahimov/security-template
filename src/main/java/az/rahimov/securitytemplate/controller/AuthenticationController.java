package az.rahimov.securitytemplate.controller;

import az.rahimov.securitytemplate.auth.AuthenticationRequest;
import az.rahimov.securitytemplate.auth.AuthenticationResponse;
import az.rahimov.securitytemplate.auth.RegisterRequest;
import az.rahimov.securitytemplate.model.User;
import az.rahimov.securitytemplate.repository.UserRepository;
import az.rahimov.securitytemplate.service.AuthenticationService;
import az.rahimov.securitytemplate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

@PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
){
return authService.register(request);
}

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
return authService.authenticate(request);
    }
}
