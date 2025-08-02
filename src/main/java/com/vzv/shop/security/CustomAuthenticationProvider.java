package com.vzv.shop.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder encoder;
    private final CustomUserDetailsService customUDService;

    public CustomAuthenticationProvider(PasswordEncoder encoder, CustomUserDetailsService customUDService) {
        this.encoder = encoder;
        this.customUDService = customUDService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails details = customUDService.loadUserByUsername(username);
        if (!encoder.matches(password, details.getPassword())){
            throw new BadCredentialsException("Wrong password!");
        } else {
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    details.getUsername(),
                    details.getPassword(),
                    details.getAuthorities()
            );
            log.info("Authentication token created! {}", details);
            return auth;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
