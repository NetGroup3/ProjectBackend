package com.example.NetProjectBackend.service.mail;

import com.example.NetProjectBackend.models.entity.User;
import com.example.NetProjectBackend.models.Verify;

public interface Mail {
    void confirmationCode(String link, String email);

    boolean recoveryCode(String link, String email);

    boolean sendNewPassword(String link, String password, User user, Verify verify);

    boolean sendModeratorPassword(String password, String email);

    Verify readByCode(String code);

    void deleteCode(int ownerId);

    boolean checkData(Verify verify);

}
