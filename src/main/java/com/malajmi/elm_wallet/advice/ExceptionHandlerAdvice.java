package com.malajmi.elm_wallet.advice;

import com.malajmi.elm_wallet.exceptions.DuplicateUserException;
import com.malajmi.elm_wallet.exceptions.InvalidParametersException;
import com.malajmi.elm_wallet.exceptions.NotFoundException;
import com.malajmi.elm_wallet.exceptions.UserNotFoundException;
import com.malajmi.elm_wallet.model.ApiErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ExceptionHandlerAdvice {

        @ExceptionHandler(NotFoundException.class)
        public ResponseEntity<ApiErrorResponse> handleNotFoundException(NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiErrorResponse(NOT_FOUND.value(), e.getMessage()));
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiErrorResponse> handleRequestNotValidException(MethodArgumentNotValidException e) {

            List<String> errors = new ArrayList<>();
            e.getBindingResult()
                    .getFieldErrors().forEach(error -> errors.add(error.getField() + ": " + error.getDefaultMessage()));
            e.getBindingResult()
                    .getGlobalErrors() //Global errors are not associated with a specific field but are related to the entire object being validated.
                    .forEach(error -> errors.add(error.getObjectName() + ": " + error.getDefaultMessage()));

            String message = "Validation of request failed: %s".formatted(String.join(", ", errors));
            return ResponseEntity.status(BAD_REQUEST).body(new ApiErrorResponse(BAD_REQUEST.value(), message));
        }

        @ExceptionHandler(BadCredentialsException.class)
        public ResponseEntity<ApiErrorResponse> handleBadCredentialsException() {
            return ResponseEntity.status(UNAUTHORIZED)
                    .body(new ApiErrorResponse(UNAUTHORIZED.value(), "Invalid username or password"));
        }

        @ExceptionHandler(DuplicateUserException.class)
        public ResponseEntity<ApiErrorResponse> handleDuplicateException(DuplicateUserException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiErrorResponse(CONFLICT.value(), e.getMessage()));
        }

        @ExceptionHandler(InvalidParametersException.class)
        public ResponseEntity<ApiErrorResponse> handleInvalidParametersException(InvalidParametersException e) {
            return ResponseEntity.status(BAD_REQUEST).body(new ApiErrorResponse(BAD_REQUEST.value(), e.getMessage()));
        }

        @ExceptionHandler(UserNotFoundException.class)
        public ResponseEntity<ApiErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiErrorResponse(NOT_FOUND.value(), e.getMessage()));
        }

        @ExceptionHandler(InternalAuthenticationServiceException.class)
        public ResponseEntity<ApiErrorResponse> handleInternalAuthenticationServiceException(InternalAuthenticationServiceException e) {
            return ResponseEntity.status(UNAUTHORIZED).body(new ApiErrorResponse(UNAUTHORIZED.value(), e.getMessage()));
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiErrorResponse> handleUnknownException(Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiErrorResponse(INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
}
