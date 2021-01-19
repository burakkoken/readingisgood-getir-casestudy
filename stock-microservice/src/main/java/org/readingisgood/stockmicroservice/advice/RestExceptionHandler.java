package org.readingisgood.stockmicroservice.advice;

import org.readingisgood.stockmicroservice.exception.StockNotFoundException;
import org.readingisgood.stockmicroservice.model.response.APIErrorDetail;
import org.readingisgood.stockmicroservice.model.response.APIErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler(StockNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(StockNotFoundException ex, WebRequest request) {
        APIErrorResponse apiErrorResponse = new APIErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.NOT_FOUND);
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
