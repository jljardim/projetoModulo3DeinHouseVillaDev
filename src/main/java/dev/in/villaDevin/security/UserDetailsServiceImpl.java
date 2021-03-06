package dev.in.villaDevin.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.in.villaDevin.controller.service.UserService;
import dev.in.villaDevin.to.UserTO;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;
    
    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    public UserDetailsImpl authenticated() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return new UserDetailsImpl((String) authentication.getPrincipal(), null,
                    authentication.getAuthorities());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            final UserTO user = userService.getUser(username);
            return new UserDetailsImpl(user.getEmail(), user.getPassword(), user.getRoles());
        } catch (Exception e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
