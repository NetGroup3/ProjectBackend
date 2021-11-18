package com.example.NetProjectBackend.services.mail;

import com.example.NetProjectBackend.models.User;
import com.example.NetProjectBackend.models.Verify;

public interface Mail {
    void confirmationCode(String link, String email);

    boolean recoveryCode(String link, String email);

    boolean sendNewPassword(String link, String password, User user, Verify verify);

    Verify readByCode(String code);

    void deleteCode(int ownerId);

    boolean checkData(Verify verify);
}
