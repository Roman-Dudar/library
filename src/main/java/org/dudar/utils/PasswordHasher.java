package org.dudar.utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordHasher {

    private static final Logger LOGGER = LogManager.getLogger(PasswordHasher.class);

    private static PasswordHasher instance = new PasswordHasher();

    public static PasswordHasher getInstance() {
        return instance;
    }

    private PasswordHasher() {
    }

    public String hashPassword(String passwordToHash) {
        String salt = null;
        try {
            salt = createSalt();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Salt generating failure.", e);
        }
        return hashPassword(passwordToHash, salt);
    }

    private String hashPassword(String passwordToHash, String salt) {
        String result = null;
        try {
            passwordToHash = salt + passwordToHash;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(passwordToHash.getBytes("UTF-8"));
            result = bytesToString(hash) + ":" + salt;
        } catch (Exception e) {
            LOGGER.error("Password hashing failure.", e);
        }
        return result;
    }

    private String createSalt() throws NoSuchAlgorithmException {
        byte[] salt = new byte[32];
        SecureRandom.getInstance("NativePRNG").nextBytes(salt);
        return bytesToString(salt);
    }

    private String bytesToString(byte[] byteArr){
        StringBuilder hexStrBuilder = new StringBuilder();
        for (int i = 0; i < byteArr.length; i++) {
            String hex = Integer.toHexString(0xff & byteArr[i]);
            if (hex.length() == 1) hexStrBuilder.append('0');
            hexStrBuilder.append(hex);
        }
        return hexStrBuilder.toString();
    }

    public boolean verifyPassword(String realPassword, String stringToVerify){
        String salt = realPassword.split(":")[1];
        if (realPassword.equals(hashPassword(stringToVerify, salt))) return true;
        else return false;
    }
}
