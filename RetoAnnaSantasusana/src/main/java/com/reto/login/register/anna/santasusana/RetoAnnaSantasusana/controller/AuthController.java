package com.reto.login.register.anna.santasusana.RetoAnnaSantasusana.controller;

import com.reto.login.register.anna.santasusana.RetoAnnaSantasusana.model.dto.LoginRequest;
import com.reto.login.register.anna.santasusana.RetoAnnaSantasusana.model.dto.RegisterRequest;
import com.reto.login.register.anna.santasusana.RetoAnnaSantasusana.model.exceptions.BadCredentialsException;
import com.reto.login.register.anna.santasusana.RetoAnnaSantasusana.model.exceptions.EmailAlreadyExistsException;
import com.reto.login.register.anna.santasusana.RetoAnnaSantasusana.model.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final AuthService userService;

    @PostMapping("/auth/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest request) {

        try {
            userService.register(request);
            return ResponseEntity.ok("User successfully registered.");
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {

        try {
            return ResponseEntity.ok(userService.authenticate(loginRequest));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     Método simple para comprobar que se necesita estar registrado y logueado para acceder a él
     */
    @GetMapping("/users/count")
    public ResponseEntity<String> getUserCount() {
        int count = userService.numberOfUsers();
        return ResponseEntity.ok("We have a total of " + count + " registered users");
    }

}

