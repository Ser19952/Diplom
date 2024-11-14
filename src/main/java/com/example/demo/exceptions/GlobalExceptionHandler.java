package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MaxUploadSizeExceededException.class, })
    public ResponseEntity<ErrorResponse> catchMaxUploadSizeExceeded(MaxUploadSizeExceededException e){
        return new ResponseEntity<>(ErrorResponse.builder().message(e.getMessage()).build(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> catchInvalidJwtException(InvalidJwtException e){
        return new ResponseEntity<>(ErrorResponse.builder().message(e.getMessage()).build(),HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> serverException (Exception e){
        return new ResponseEntity<>(ErrorResponse.builder().message(e.getMessage()).build(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler({UserNotFoundException.class, UsernameNotFoundException.class})
    public ResponseEntity<ErrorResponse> userNotFoundExceptionHandler (Exception e){
        return new ResponseEntity<>(ErrorResponse.builder().message(e.getMessage()).build(),HttpStatus.NOT_FOUND);
    }

}
