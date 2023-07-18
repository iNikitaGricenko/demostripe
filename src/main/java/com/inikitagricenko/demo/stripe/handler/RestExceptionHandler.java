package com.inikitagricenko.demo.stripe.handler;

import com.inikitagricenko.demo.stripe.handler.error.DefaultBackendException;
import com.inikitagricenko.demo.stripe.handler.error.ErrorBody;
import com.inikitagricenko.demo.stripe.handler.error.InvalidPaymentMethod;
import com.inikitagricenko.demo.stripe.handler.error.ValidationErrorBody;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.StripeException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleConflict(RuntimeException ex) {
        return new ResponseEntity<>("Something went wrong", FORBIDDEN);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errors = exception.getBindingResult().getAllErrors().stream()
            .map(ObjectError::getDefaultMessage).toList();
        HttpStatus httpStatus = HttpStatus.valueOf(status.value());
        ValidationErrorBody body = new ValidationErrorBody(httpStatus, request, errors);
        return handleExceptionInternal(exception, body, headers, status, request);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleViolationAccess(ValidationException exception, WebRequest request) {
        return handleException(exception, request, FORBIDDEN);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<Object> handleInvalidRequest(InvalidRequestException exception, WebRequest request) {
        return handleException(exception, request, FORBIDDEN);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException exception, WebRequest request) {
        return handleException(exception, request, BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPaymentMethod.class)
    public ResponseEntity<Object> handleInvalidPaymentMethod(InvalidPaymentMethod exception, WebRequest request) {
        return handleException(exception, request, BAD_REQUEST);
    }

    @ExceptionHandler(DefaultBackendException.class)
    public ResponseEntity<Object> handleDefaultBackendException(DefaultBackendException exception, WebRequest request) {
        return handleException(exception, request, FORBIDDEN);
    }

    @ExceptionHandler(StripeException.class)
    public ResponseEntity<Object> handleStripeException(StripeException exception, WebRequest request) {
        return handleException(exception, request, HttpStatus.valueOf(exception.getStatusCode()));
    }

    private ResponseEntity<Object> handleException(Exception exception, WebRequest request, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        ErrorBody body = new ErrorBody(status, request, exception.getMessage());
        return handleExceptionInternal(exception, body, headers, status, request);
    }

}
