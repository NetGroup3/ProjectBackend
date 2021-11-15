package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.services.mail.Mail;
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
}
