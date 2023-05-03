package com.reto.login.register.anna.santasusana.RetoAnnaSantasusana.model.service;

import com.reto.login.register.anna.santasusana.RetoAnnaSantasusana.model.domain.Role;
import com.reto.login.register.anna.santasusana.RetoAnnaSantasusana.model.domain.User;
import com.reto.login.register.anna.santasusana.RetoAnnaSantasusana.model.dto.JwtResponse;
import com.reto.login.register.anna.santasusana.RetoAnnaSantasusana.model.dto.LoginRequest;
import com.reto.login.register.anna.santasusana.RetoAnnaSantasusana.model.dto.RegisterRequest;
import com.reto.login.register.anna.santasusana.RetoAnnaSantasusana.model.exceptions.BadCredentialsException;
import com.reto.login.register.anna.santasusana.RetoAnnaSantasusana.model.exceptions.EmailAlreadyExistsException;
import com.reto.login.register.anna.santasusana.RetoAnnaSantasusana.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final String BAD_CREDENTIALS = "Invalid username/password";


    public void register(RegisterRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isPresent()) {
            throw new EmailAlreadyExistsException("This email is already registered.");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
    }

    public JwtResponse authenticate(LoginRequest request) throws AuthenticationException {
        User user = null;
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isPresent()) {
            user = userOptional.get();
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new BadCredentialsException(BAD_CREDENTIALS);
            }
         } else {
            throw new BadCredentialsException(BAD_CREDENTIALS);
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new BadCredentialsException(BAD_CREDENTIALS);
        }

        String jwtToken = jwtService.generateToken(user);

        return JwtResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     Método que utilizamos en el controller para demostrar que sólo se puede acceder a él si estas
     registrado y logueado
     */
    public int numberOfUsers() {
        return (int) userRepository.count();
    }

}

