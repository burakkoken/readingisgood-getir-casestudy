package org.readingisgood.ordermicroservice.advice;

import org.readingisgood.ordermicroservice.exception.OrderNotFoundException;
import org.readingisgood.ordermicroservice.exception.StockDataNotObtainedException;
import org.readingisgood.ordermicroservice.exception.StockNotEnoughException;
import org.readingisgood.ordermicroservice.exception.TokenInvalidException;
import org.readingisgood.ordermicroservice.model.response.APIErrorDetail;
import org.readingisgood.ordermicroservice.model.response.APIErrorResponse;
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

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Object> handleOrderNotFoundException(OrderNotFoundException ex, WebRequest request) {
        APIErrorResponse apiErrorResponse = new APIErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(StockDataNotObtainedException.class)
    public ResponseEntity<Object> handleStockDataNotObtainedException(StockDataNotObtainedException ex, WebRequest request) {
        APIErrorResponse apiErrorResponse = new APIErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StockNotEnoughException.class)
    public ResponseEntity<Object> handleStockNotEnoughException(StockNotEnoughException ex, WebRequest request) {
        APIErrorResponse apiErrorResponse = new APIErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TokenInvalidException.class)
    public ResponseEntity<Object> handleTokenInvalidException(TokenInvalidException ex, WebRequest request) {
        APIErrorResponse apiErrorResponse = new APIErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

}
