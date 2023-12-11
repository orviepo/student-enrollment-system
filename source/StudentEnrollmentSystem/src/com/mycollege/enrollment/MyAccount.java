package com.mycollege.enrollment;

import java.util.Arrays;

public class MyAccount {

  public static final int VALID           = 0;
  public static final int INVALID_VALUE   = 2;
  public static final int INVALID_LENGTH  = 5;
  public static final int MISMATCH        = 6;

  private static boolean invalidValue;
  private static boolean lengthInvalid;
  private static boolean notMatched;
  
  // Email regular expression
  private static final String EMAILRE = "^[a-zA-Z0-9_]+([.-]?[a-zA-Z0-9_])*@[a-zA-Z0-9_]+([.-]?[a-zA-Z0-9_])*(.[a-zA-Z0-9_]{2,3})+";

  public static int checkTokens(String value, char[] key, char[] pair, int minLength) {

    invalidValue    = !value.matches(EMAILRE);
    lengthInvalid   = key.length < minLength;
    notMatched      = !Arrays.equals(key, pair);
    
    if (invalidValue) {
      return INVALID_VALUE;
    }
    else if (lengthInvalid) {
      return INVALID_LENGTH;
    }
    else if(notMatched) {
      return MISMATCH;
    }
    else {
      return VALID;
    }
  }

  public static int checkTokens(String value, char[] key, int minLength) {

    invalidValue    = value.matches(EMAILRE);
    lengthInvalid   = (key.length < minLength);

    if (invalidValue) {
      return INVALID_VALUE;
    }
    else if (lengthInvalid) {
      return INVALID_LENGTH;
    }
    else {
      return VALID;
    }
  }

  public static int checkTokens(byte[] key, byte[] pair) {

    notMatched      = !Arrays.equals(key, pair);

    if (notMatched) {
      return MISMATCH;
    }
    else {
      return VALID;
    }
  }

}
