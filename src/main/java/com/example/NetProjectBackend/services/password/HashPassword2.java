package com.example.NetProjectBackend.services.password;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPassword2 {
//    public static void main(String[] args) {
//        System.out.println(doHashing("12345678"));
//    }

    public static String doHashing(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA");

            messageDigest.update(password.getBytes());

            byte[] resultByteArray = messageDigest.digest();

            StringBuilder hashPassword = new StringBuilder();

            for (byte b : resultByteArray) {
                hashPassword.append(String.format("%02x", b));
            }

            return hashPassword.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }
}