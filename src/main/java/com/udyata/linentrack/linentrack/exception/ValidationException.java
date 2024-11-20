package com.udyata.linentrack.linentrack.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException {

    private final HttpStatus status;
    private final List<String> errors;

    public ValidationException(HttpStatus status, String message, List<String> errors) {
        super(message);
        this.status = status;
        this.errors = errors;
    }

}
