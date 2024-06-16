package com.amalvadkar.ihms;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TestUtils {

    public static String toSha256Hash(String content){
        try {
            // Create a MessageDigest instance for SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Perform the hash computation
            byte[] encodedHash = digest.digest(content.getBytes());

            // Convert byte array into a hex string
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
