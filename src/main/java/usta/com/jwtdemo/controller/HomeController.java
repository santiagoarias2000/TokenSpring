package usta.com.jwtdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import usta.com.jwtdemo.model.JwtRequest;
import usta.com.jwtdemo.model.JwtResponse;
import usta.com.jwtdemo.service.UserService;
import usta.com.jwtdemo.utility.jwtUtility;

@RestController
public class HomeController {

    @Autowired
    private jwtUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() {
        return "Welcome user, access session";
    }

    @PostMapping("/authenticate")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
            final UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUsername());
            final String token = jwtUtility.generateToken(userDetails);
            System.out.println("You see to token in the postman: "+ token.length() + " Bytes");
            System.out.println("The credentials, userName: "+ jwtRequest.getUsername() +", password: "+ jwtRequest.getPassword());

        } catch (BadCredentialsException e) {
            System.out.println("Your push bad credentials in the login");
            throw new Exception("Push bad credentials, please enter your credentials", e);

        }

        final UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUsername());
        final String token = jwtUtility.generateToken(userDetails);

        return new JwtResponse(token);
    }
}
