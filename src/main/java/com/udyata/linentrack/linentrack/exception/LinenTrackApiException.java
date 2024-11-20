package com.udyata.linentrack.linentrack.exception;
import org.springframework.http.HttpStatus;
public class LinenTrackApiException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public LinenTrackApiException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public LinenTrackApiException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
