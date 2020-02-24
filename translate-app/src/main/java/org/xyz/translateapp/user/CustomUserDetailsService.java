package org.xyz.translateapp.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> userByName = userRepo.findUserByName(userName);
        if(!userByName.isPresent()) {
            throw new UserNotFoundException(String.format("User with name '%s' not found", userName));
        }

        return new AppUserDetails(userByName.get());
    }
}
