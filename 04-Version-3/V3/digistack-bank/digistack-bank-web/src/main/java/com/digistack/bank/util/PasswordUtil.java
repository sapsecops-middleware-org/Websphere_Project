package com.digistack.bank.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * PasswordUtil
 *
 * SHA-256 + per-user salt password hashing utility.
 * Chosen for Version 2 for simplicity (built into Java, no extra
 * library dependency). Not the strongest option for production
 * password storage (BCrypt/Argon2 are purpose-built and slower-by-design,
 * which resists brute-force attacks better) - this is a deliberate,
 * documented simplification for this learning project.
 */
public class PasswordUtil {

    /**
     * Hashes a plaintext password combined with a salt, using SHA-256.
     *
     * @param password the plaintext password to hash
     * @param salt     the per-user random salt (hex string)
     * @return the resulting hash, as a 64-character lowercase hex string
     */
    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest((salt + password).getBytes("UTF-8"));
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            // SHA-256 and UTF-8 are both guaranteed available on every
            // standard Java installation, so this branch should never
            // actually execute - but Java requires we handle the
            // checked exception.
            throw new RuntimeException("Unexpected error computing password hash", e);
        }
    }

    /**
     * Converts a byte array into a lowercase hex string.
     * E.g. bytes [0x4c, 0x7b] becomes the string "4c7b".
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}