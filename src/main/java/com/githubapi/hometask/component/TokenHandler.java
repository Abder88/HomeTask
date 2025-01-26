package com.githubapi.hometask.component;

import java.security.MessageDigest;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

@Component
public class TokenHandler {

//using https://acte.ltd/utils/randomkeygen
  private static final String SECRET_KEY = "yCtnewaH2rSuDb4Pv8Xyx3vmzWGEQz0H";

  private static SecretKeySpec getAESKey(String key) throws Exception {
    MessageDigest sha = MessageDigest.getInstance("SHA-256");
    byte[] keyBytes = sha.digest(key.getBytes("UTF-8"));
    return new SecretKeySpec(keyBytes, 0, 21, "AES");
  }

  public static String encryptToken(String token) throws Exception {
    Cipher cipher = Cipher.getInstance("AES");
    SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
    cipher.init(Cipher.ENCRYPT_MODE, keySpec);
    byte[] encrypted = cipher.doFinal(token.getBytes());
    return Base64.getEncoder().encodeToString(encrypted);
  }

  public static String decryptToken(String encryptedToken) throws Exception {
    Cipher cipher = Cipher.getInstance("AES");
    SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
    cipher.init(Cipher.DECRYPT_MODE, keySpec);
    byte[] decoded = Base64.getDecoder().decode(encryptedToken);
    return new String(cipher.doFinal(decoded));
  }
}