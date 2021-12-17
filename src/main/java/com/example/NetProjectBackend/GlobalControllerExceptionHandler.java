package com.example.NetProjectBackend;

import com.example.NetProjectBackend.exeptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmailAlreadyUseException.class)
    public ResponseEntity<String> handleEmailAlreadyUse(EmailAlreadyUseException emailAlreadyUseException){
        return new ResponseEntity<String>("This email is already in use", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<String> handleEmailNotFound(EmailNotFoundException emailNotFoundException){
        return new ResponseEntity<String>("No such email was found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmptyInputException.class)
    public ResponseEntity<String> handleEmptyInput(EmptyInputException emptyInputException){
        return new ResponseEntity<String>("Input field is Empty, Please look into it", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElement(NoSuchElementException noSuchElementException){
        return new ResponseEntity<String>("No value is present in DB, please change your request", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<String> handleIncorrectPassword(){
        return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundItemException.class)
    public ResponseEntity<String> handleNotFoundElement(NotFoundItemException notFoundElement){
        return new ResponseEntity<String>("This item not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistItemException.class)
    public ResponseEntity<String> handleAlreadyExistItemException(AlreadyExistItemException alreadyExistItem){
        return new ResponseEntity<String>("This item already exist", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FriendAlreadyAddedException.class)
    public ResponseEntity<String> handleFriendAlreadyAddedException(){
        return new ResponseEntity<>("Friend already added", HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        return new ResponseEntity<Object>("Please change your http method", HttpStatus.NOT_FOUND);
    }

}
