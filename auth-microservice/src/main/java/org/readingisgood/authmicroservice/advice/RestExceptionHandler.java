package org.readingisgood.authmicroservice.advice;

import org.readingisgood.authmicroservice.exception.UserAlreadyRegisteredException;
import org.readingisgood.authmicroservice.exception.UserNotFoundException;
import org.readingisgood.authmicroservice.model.response.APIErrorDetail;
import org.readingisgood.authmicroservice.model.response.APIErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        APIErrorResponse apiErrorResponse = new APIErrorResponse(status, ex.getMessage());
        return new ResponseEntity<>(apiErrorResponse, headers, status);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        APIErrorResponse apiErrorResponse = new APIErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<Object> handleInternalAuthenticationServiceException(InternalAuthenticationServiceException ex, WebRequest request) {
        APIErrorResponse apiErrorResponse = new APIErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ResponseEntity<Object> handleUserAlreadyRegisteredException(UserAlreadyRegisteredException ex, WebRequest request) {
        APIErrorResponse apiErrorResponse = new APIErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserAlreadyRegisteredException(UserNotFoundException ex, WebRequest request) {
        APIErrorResponse apiErrorResponse = new APIErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.UNAUTHORIZED);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        List<APIErrorDetail> apiErrorDetails = exception.getFieldErrors().stream()
                .map(e -> new APIErrorDetail(e.getDefaultMessage(), exception.getClass().getSimpleName()))
                .collect(Collectors.toList());

        APIErrorResponse apiErrorResponse = new APIErrorResponse(HttpStatus.BAD_REQUEST, "Arguments Not Valid", apiErrorDetails);
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

}
