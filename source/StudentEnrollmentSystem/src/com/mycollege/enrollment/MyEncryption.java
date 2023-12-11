package com.mycollege.enrollment;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Password-Based Key Derivative Function 2 (PBKDF2)
 */
public class MyEncryption {

  private byte[] salt;
  private byte[] hash;
  
  public MyEncryption(char[] password) {
    // Generate random salt (byte string)
    SecureRandom random = new SecureRandom();
    salt = new byte[16];
    random.nextBytes(salt);

    try {
      hash = encrypt(password, salt);
    }
    catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    catch (InvalidKeySpecException e) {
      e.printStackTrace();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public MyEncryption(char[] password, byte[] salt) {
    this.salt = salt;

    try {
      hash = encrypt(password, salt);
    }
    catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    catch (InvalidKeySpecException e) {
      e.printStackTrace();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public byte[] getHash() {
    return hash;
  }

  public byte[] getSalt() {
    return salt;
  }

  private byte[] encrypt(char[] password, byte[] salt) 
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    // Encypt password
    KeySpec spec = 
        new PBEKeySpec(password, salt, 65536, 128);
    SecretKeyFactory factory = 
        SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    
    // Encrypted password
    SecretKey key = factory.generateSecret(spec);
    hash = key.getEncoded();
    
    return hash;
  }

}

