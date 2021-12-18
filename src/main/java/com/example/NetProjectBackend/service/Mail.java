package com.example.NetProjectBackend.service;

import com.example.NetProjectBackend.models.entity.User;
import com.example.NetProjectBackend.models.Verify;

public interface Mail {
    void confirmationCode(String email, boolean recovery);

    boolean recoveryCode(String email);

    boolean sendNewPassword(String password, User user, Verify verify);

    boolean sendModeratorPassword(String password, String email);

    Verify readByCode(String code);

    void deleteCode(int ownerId);

    boolean checkData(Verify verify);

}
