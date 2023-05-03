package com.reto.login.register.anna.santasusana.RetoAnnaSantasusana.model.exceptions;

import java.util.List;

public record ErrorResponse(String message, List<ValidationError> errors) {
}
