package com.ezfun.guess.spring.v2.bean;

/**
 * @author SoySauce
 * @date 2020/6/17
 */
public class PasswordDecodeable {

    private static final String PUBLIC_KEY = "123";
    private static final String SALT = "ABC";
    private String password;

    String getEncodePassword() {
        byte[] bytes = SALT.getBytes();
        for (int i = 0, len = bytes.length; i < len; i++) {
            bytes[i] = (byte) (bytes[i] ^ Long.parseLong(PUBLIC_KEY, 36));
        }
        return new String(bytes);
    }

    public void setDecodePassword(String decodePassword) {
        this.password = decodePassword;
    }

    public String getPassword() {
        return password;
    }
}
