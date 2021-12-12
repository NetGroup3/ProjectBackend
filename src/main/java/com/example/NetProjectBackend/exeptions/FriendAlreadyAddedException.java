package com.example.NetProjectBackend.exeptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
public class FriendAlreadyAddedException extends RuntimeException {

    private String message;
    private HttpStatus httpStatus;

    public FriendAlreadyAddedException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
