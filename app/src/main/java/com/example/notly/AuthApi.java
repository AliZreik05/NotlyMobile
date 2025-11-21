// AuthApi.java
package com.example.notly;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {

    @POST("signup/")   // baseUrl + "signup/"  -> matches your router prefix="/signup"
    Call<SignUpResponse> signUp(@Body SignUpRequest request);
    @POST("signin/")
    Call<LoginResponse> login(@Body LoginRequest request);
}
