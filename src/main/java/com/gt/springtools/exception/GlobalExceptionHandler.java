package com.gt.springtools.exception;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseError> handleEntityNotFoundException(EntityNotFoundException e) {
        ResponseError response = new ResponseError(HttpStatus.NOT_FOUND, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<ResponseError> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        ResponseError response = new ResponseError(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(EntityPersistenceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseError> handleEntityPersistenceException(EntityPersistenceException e) {
        ResponseError response = new ResponseError(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getFieldErrors()
                .stream().map(f -> MessageFormat.format("{0}: {1}",
                        f.getField(), f.getDefaultMessage())).toList();
        ResponseError response = new ResponseError(HttpStatus.BAD_REQUEST, e.getBody().getDetail());
        response.setErrors(errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseError> handleNoResourceFoundException(NoResourceFoundException e) {
        ResponseError response = new ResponseError(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseError> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        ResponseError response = new ResponseError(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseError> handleMethodValidationException(HandlerMethodValidationException e) {
        ResponseError response = new ResponseError(HttpStatus.BAD_REQUEST, e.getReason());
        response.setErrors(new ArrayList<>());

        e.getAllValidationResults()
                .forEach(validation -> validation.getResolvableErrors()
                        .stream()
                        .filter(r -> !(r instanceof FieldError))
                        .filter(r -> r.getArguments() != null)
                        .forEach(r -> response.getErrors()
                                .add(MessageFormat.format("{0}: invalid",
                                        ((DefaultMessageSourceResolvable) r.getArguments()[0]).getDefaultMessage()))));

        e.getAllValidationResults()
                .forEach(validation -> validation.getResolvableErrors()
                        .stream()
                        .filter(FieldError.class::isInstance)
                        .forEach(r -> response.getErrors()
                                .add(MessageFormat.format("{0}: {1}",
                                        ((FieldError) r).getField(),
                                        ((FieldError) r).getDefaultMessage())))
                );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CallNotPermittedException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ResponseEntity<ResponseError> handleCircuitOpenException(CallNotPermittedException e) {
        ResponseError response = new ResponseError(HttpStatus.SERVICE_UNAVAILABLE,
                "Method temporarily unavailable. Please try again in a few seconds");
        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseError> handleException(Exception e) {
        ResponseError response = new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
