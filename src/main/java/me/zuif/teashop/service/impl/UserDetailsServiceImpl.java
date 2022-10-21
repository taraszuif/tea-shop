package me.zuif.teashop.service.impl;

import me.zuif.teashop.model.User;
import me.zuif.teashop.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUserName(username);

        if (user != null) {
            Set<GrantedAuthority> authorities = new HashSet<>();
            if (user.getRole().equalsIgnoreCase("ADMIN")) {
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            } else if (user.getRole().equals("USER")) {
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            } else {
                authorities.add(new SimpleGrantedAuthority("ROLE_GUEST"));
            }
            logger.debug(String.format("User with name: %s and password: %s created.", user.getUserName(), user.getPassword()));
            return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities);
        } else {
            throw new UsernameNotFoundException("User " + username + " not found!");
        }
    }


}