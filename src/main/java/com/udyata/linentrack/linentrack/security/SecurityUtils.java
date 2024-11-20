package com.udyata.linentrack.linentrack.security;

import com.udyata.linentrack.linentrack.entity.user.User;
import com.udyata.linentrack.linentrack.repository.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    private final UserRepository userRepository;

    public SecurityUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        } else {
            return null; // or throw an exception
        }
    }

    public User getCurrentAuthenticatedUser() {
        String username = getCurrentUsername();
        if (username != null) {
            return userRepository.findByUsernameOrEmail(username, username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        } else {
            return null; // or throw an exception
        }
    }
}
