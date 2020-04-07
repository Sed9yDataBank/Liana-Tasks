package com.kanbanedchain.lianatasks.Controllers;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.validation.Valid;

import com.kanbanedchain.lianatasks.Exceptions.ApiRequestException;
import com.kanbanedchain.lianatasks.Messages.Requests.LoginForm;
import com.kanbanedchain.lianatasks.Messages.Requests.SignUpForm;
import com.kanbanedchain.lianatasks.Messages.Responses.JWTResponse;
import com.kanbanedchain.lianatasks.Models.Role;
import com.kanbanedchain.lianatasks.Models.RoleName;
import com.kanbanedchain.lianatasks.Models.User;
import com.kanbanedchain.lianatasks.Repositories.RoleRepository;
import com.kanbanedchain.lianatasks.Repositories.UserRepository;
import com.kanbanedchain.lianatasks.Securities.JWT.JWTProvider;
import com.kanbanedchain.lianatasks.Utils.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JWTProvider jwtProvider;

    @Autowired
    EmailValidator emailValidator;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UUID userId = userRepository.getIdByUsername(userDetails.getUsername());
        JWTResponse jwtRes = new JWTResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities(), userId);
        return ResponseEntity.ok(jwtRes);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new ApiRequestException(signUpRequest.getUsername() + " Is Not Valid");        }

        if (!emailValidator.test(signUpRequest.getEmail())) {
            throw new ApiRequestException(signUpRequest.getEmail() + " Is Not Valid");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new ApiRequestException(signUpRequest.getEmail() + " Is Already In Use By Other Users");
        }

        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<Role> roles = new HashSet<>();

        if( signUpRequest.getUser().equalsIgnoreCase("U")) {
            Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
            roles.add(userRole);

        } else if( signUpRequest.getUser().equalsIgnoreCase("A")) {
            Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
            roles.add(adminRole);
        }

        user.setRoles(roles);
        userRepository.save(user);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
