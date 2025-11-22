// AuthApi.java
package com.example.notly;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AuthApi {
    @GET("lectures/")
    Call<List<LectureResponse>> getLectures(@Query("user_id") int userId);
    @POST("signup/")   // baseUrl + "signup/"  -> matches your router prefix="/signup"
    Call<SignUpResponse> signUp(@Body SignUpRequest request);
    @POST("signin/")
    Call<LoginResponse> login(@Body LoginRequest request);
    @POST("QuizGenerator/")
    Call<QuizResponse> createQuiz(@Body QuizRequest payload);

    @GET("history/")
    Call<List<ExamHistoryResponse>> getHistory(@Query("user_id") int userId);


}
