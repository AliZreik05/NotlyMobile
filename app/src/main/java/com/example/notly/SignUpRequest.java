// SignUpRequest.java
package com.example.notly;

public class SignUpRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String verifyPassword;
    private boolean rememberMe;

    public SignUpRequest(String firstName, String lastName, String email,
                         String password, String verifyPassword, boolean rememberMe) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.verifyPassword = verifyPassword;
        this.rememberMe = rememberMe;
    }
}
