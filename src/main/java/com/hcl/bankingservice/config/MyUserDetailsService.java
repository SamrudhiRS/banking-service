package com.hcl.bankingservice.config;

import com.hcl.bankingservice.model.User;
import com.hcl.bankingservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty())
        {
            throw new UsernameNotFoundException(username);
        }
        final User user = optionalUser.get();

        MyUserDetails myUserDetails = new MyUserDetails(user.getUsername(), user.getPassword(), user.getActive(), user.getRole());
        return myUserDetails;
    }
}


