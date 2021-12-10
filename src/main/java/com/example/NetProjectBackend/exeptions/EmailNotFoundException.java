package com.example.NetProjectBackend.exeptions;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class EmailNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    private String errorCode;
    private String errorMessage;
}
