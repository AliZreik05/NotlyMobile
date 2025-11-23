package com.example.notly;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitAPI {

    private static Retrofit retrofit = null;
    private static AuthApi authApi = null;

    public static AuthApi getAuthApi() {
        if (authApi == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://notly.onrender.com/") // MUST end with slash
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            authApi = retrofit.create(AuthApi.class);
        }
        return authApi;
    }
}
