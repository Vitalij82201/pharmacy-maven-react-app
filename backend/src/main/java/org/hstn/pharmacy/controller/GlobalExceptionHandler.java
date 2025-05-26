package org.hstn.pharmacy.controller;

import jakarta.validation.ConstraintViolationException;
import org.hstn.pharmacy.dto.dtoUser.ErrorResponseDto;
import org.hstn.pharmacy.exceptions.AlreadyExistException;
import org.hstn.pharmacy.exceptions.NotFoundException;
import org.hstn.pharmacy.exceptions.RestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorResponseDto> buildErrorResponse(String message, HttpStatus status, String errorType) {
        ErrorResponseDto response = ErrorResponseDto.builder()
                .message(message)
                .statusCode(status.value())
                .errorType(errorType)
                .build();
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFoundException(NotFoundException e) {
        return buildErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND, "NotFoundException");
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ErrorResponseDto> handleAlreadyExistException(AlreadyExistException e) {
        return buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "AlreadyExistException");
    }

    @ExceptionHandler(RestException.class)
    public ResponseEntity<Map<String,String>> handleRestException(RestException e) {
        Map<String,String> response = new HashMap<>();
        response.put("message", e.getMessage());
        response.put("error", "Exception Type: " + e.getClass().getSimpleName());

        return new ResponseEntity<>(response,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleConstraintViolationException(ConstraintViolationException e) {
        StringBuilder responseMessage = new StringBuilder();
        e.getConstraintViolations().forEach(constraintViolation -> {
            responseMessage.append(constraintViolation.getMessage()).append("\n");
        });

        return buildErrorResponse(responseMessage.toString(), HttpStatus.BAD_REQUEST, "ConstraintViolationException");
    }
}
