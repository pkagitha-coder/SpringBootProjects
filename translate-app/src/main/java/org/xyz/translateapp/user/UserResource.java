package org.xyz.translateapp.user;

import javax.validation.Valid;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class UserResource {

    @Autowired
    UserRepo userRepo;

    private final PasswordEncoder pwdEncoder = new BCryptPasswordEncoder();

    @PostMapping("/users/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
        Optional<User> userByEmail = userRepo.findUserByEmail(user.getEmail());
        if (userByEmail.isPresent()) {
            throw new UserAlreadyExistsException(String.format("User with email '%s' already exists,Please try with another email", userByEmail.get().getEmail()));
        }
        user.setSecret(pwdEncoder.encode(user.getPassword()));
        User createdUser = userRepo.save(user);
        return new ResponseEntity<String>("Congratulations , your account is registered", HttpStatus.CREATED);
    }

    @GetMapping("/users/{email}")
    public MappingJacksonValue getByEmail(@PathVariable String email) {
        Optional<User> userByEmail = userRepo.findUserByEmail(email);
        if (!userByEmail.isPresent()) {
            throw new UserNotFoundException(String.format("User with email '%s' not found", email));
        }
        MappingJacksonValue value = new MappingJacksonValue(userByEmail.get());
        SimpleFilterProvider filters = new SimpleFilterProvider();
        filters.addFilter("CustomFilter", SimpleBeanPropertyFilter.serializeAllExcept("password","secret"));
        value.setFilters(filters);
        return value;
    }

}
