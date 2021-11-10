package com.celso.springrest.services;

import com.celso.springrest.gateway.repository.UserRepository;
import com.celso.springrest.security.AccountCredentials;
import com.celso.springrest.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticateService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;


    public Map<Object, Object> authenticate(AccountCredentials accountCredentials) {
        try {
            var username = accountCredentials.getUsername();
            var password = accountCredentials.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            var user = userRepository.findByUsername(username);
            var token = "";

            if (user != null) {
                token = jwtTokenProvider.createToken(username, user.getRoles());
            } else {
                throw new UsernameNotFoundException("Username " + username + " not found");
            }

            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);

            return model;
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalide username or password");
        }
    }
}
