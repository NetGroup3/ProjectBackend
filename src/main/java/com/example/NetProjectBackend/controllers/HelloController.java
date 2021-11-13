package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.services.mail.Mail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    final
    Mail mail;

    public HelloController(Mail mail) {
        this.mail = mail;
    }

    @GetMapping("/hello")
    public String getHello(){
        mail.sendCode("https://ourproject.space/use_code?code=", "308ty397f239uopdh3f9p823dh928dhp1280dfh89ph", "d.averianoff@gmail.com");
        return "hello world!!";
    }
}
