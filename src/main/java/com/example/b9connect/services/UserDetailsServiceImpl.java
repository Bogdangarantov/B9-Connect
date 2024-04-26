package com.example.b9connect.services;

import com.example.b9connect.entities.User;
import com.example.b9connect.exceptions.UnauthorizedAccessException;
import com.example.b9connect.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        System.out.println(login);
        User user = userRepository.findUserByLogin(login).orElseThrow(() -> new UsernameNotFoundException("Could not find user"));
        if (user.isEnabled()) {
            System.out.println(user);
            return user;
        }
        else {
            logger.info(String.format("Blocked USER with login: '%s' tried to login", login));
            throw new UnauthorizedAccessException("ACCESS DENIED, USER BLOCKED - CONTACT YOUR MANAGER");
        }
    }
}
