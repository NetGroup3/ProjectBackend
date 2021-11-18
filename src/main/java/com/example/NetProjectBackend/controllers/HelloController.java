package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.services.mail.Mail;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    //final private Mail mail;

    //public HelloController(Mail mail) {
    //    this.mail = mail;
    //}

    @GetMapping("/hello")
    public String getHello(){
        //mail.confirmationCode("https://ourproject.space/code?param=", "d.averianoff@gmail.com");
        return "hello world!!";
    }
    @GetMapping("/hello/user")
    //@PreAuthorize("hasRole('USER')")
    public String getUser(){
        return "USER HELLO";
    }
//    @PreAuthorize("hasRole('MODERATOR')")
    public String getModerator(){
        return "MODERATOR HELLO";
    }
    @PreAuthorize("hasRole('ADMIN')")
    public String getAdmin(){
        return "ADMIN HELLO";
    }
}
