package org.xyz.translateapp.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class UserResource {

    @Autowired
    UserRepo userRepo;

    @PostMapping("/user/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        Optional<User> userByEmail = userRepo.findUserByEmail(user.getEmail());
        if (userByEmail.isPresent()) {
            throw new UserAlreadyExistsException(String.format("User with email '%s' already exists,Please try with another email", userByEmail.get().getEmail()));
        }
        User createdUser = userRepo.save(user);
        return new ResponseEntity<String>("Congratulations , your account is registered", HttpStatus.CREATED);
    }

    @GetMapping("/user/hello")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<String>("Hello world !!", HttpStatus.OK);
    }

    @GetMapping("/user/{email}")
    public MappingJacksonValue getByEmail(@PathVariable String email) {
        Optional<User> userByEmail = userRepo.findUserByEmail(email);
        if (!userByEmail.isPresent()) {
            throw new UserNotFoundException(String.format("User with email '%s' not found", email));
        }
        MappingJacksonValue value = new MappingJacksonValue(userByEmail.get());
        SimpleFilterProvider filters = new SimpleFilterProvider();
        filters.addFilter("CustomFilter", SimpleBeanPropertyFilter.serializeAllExcept("password"));
        value.setFilters(filters);
        return value;
    }

}
