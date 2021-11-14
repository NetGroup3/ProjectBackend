package com.example.NetProjectBackend.services.password;

import com.example.NetProjectBackend.models.User;
import com.example.NetProjectBackend.repositories.UserRepository;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class HashPassword {

//    public static void main(String[] args)
////            throws UnsupportedEncodingException
//    {
//        String password = "asdasd";
//        String salt = "1234";
//        int iterations = 10000;
//        int keyLength = 50;
//        char[] passwordChars = password.toCharArray();
//        byte[] saltBytes = salt.getBytes();
//
//        byte[] hashedBytes = hashPassword(passwordChars, saltBytes, iterations, keyLength);
//        String hashedString = Hex.encodeHexString(hashedBytes);
//
//        System.out.println(hashedString);
//    }

    @Autowired
    UserRepository userRepository;

    String salt = "1234";
    int iterations = 10000;
    int keyLength = 50;

    public String getHashPassword(String password) {
        char[] passwordChars = password.toCharArray();
        byte[] saltBytes = salt.getBytes();

        byte[] hashedBytes = hashPassword(passwordChars, saltBytes, iterations, keyLength);
        String hashedString = Hex.encodeHexString(hashedBytes);
        System.out.println(hashedString);
        return hashedString;
    }

    public static byte[] hashPassword(final char[] password, final byte[] salt, final int iterations, final int keyLength) {

        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);
            SecretKey key = skf.generateSecret(spec);
            byte[] res = key.getEncoded();
            return res;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}
