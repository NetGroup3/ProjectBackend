package com.example.NetProjectBackend.services.mail;

import javax.mail.MessagingException;

public interface Mail {
    void sendCode(String link, String code, String email);
}
