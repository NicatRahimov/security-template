package az.rahimov.securitytemplate.service;

import az.rahimov.securitytemplate.auth.AuthenticationRequest;
import az.rahimov.securitytemplate.auth.AuthenticationResponse;
import az.rahimov.securitytemplate.auth.RegisterRequest;
import az.rahimov.securitytemplate.config.JwtService;
import az.rahimov.securitytemplate.model.User;
import az.rahimov.securitytemplate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

private final JwtService jwtService;
private final PasswordEncoder passwordEncoder;
private final UserRepository userRepository;
private final AuthenticationManager authManager;

    public ResponseEntity<AuthenticationResponse> register(RegisterRequest request) {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
        AuthenticationResponse authResponse = AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
        return new ResponseEntity<>(authResponse, HttpStatus.OK);

    }

    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
       authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
       User user = userRepository.findByEmail(request.getEmail())
               .orElseThrow(()->new UsernameNotFoundException("User not found"));

String jwtToken = jwtService.generateToken(user);

        AuthenticationResponse authResp = AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

        return new ResponseEntity<>(authResp,HttpStatus.OK);
    }
}
