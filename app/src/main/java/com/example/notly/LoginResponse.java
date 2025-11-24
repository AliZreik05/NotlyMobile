// TokenResponse.java
package com.example.notly;

public class LoginResponse {
    private String access_token;
    private String token_type;
    private int user_id;  // <-- ADD

    public String getAccessToken() { return access_token; }
    public String getTokenType() { return token_type; }
    public int getUserId() { return user_id; }
}
