// AuthApi.java
package com.example.notly;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Path;


public interface AuthApi {
    @GET("lectures/")
    Call<List<LectureResponse>> getLectures(@Query("user_id") int userId);
    @POST("signup/")   // baseUrl + "signup/"  -> matches your router prefix="/signup"
    Call<SignUpResponse> signUp(@Body SignUpRequest request);
    @POST("signin/")
    Call<LoginResponse> login(@Body LoginRequest request);
    @POST("ai/generate-quiz")
    Call<GenerateQuizResponse> generateQuiz(
            @Query("user_id") int userId,
            @Body QuizGenerationRequest request
    );
    @POST("ai/generate-flashcards")
    Call<FlashcardGenerationResponse> generateFlashcards(
            @Query("user_id") int userId,
            @Body FlashcardGenerationRequest request
    );

    @GET("exam/{exam_id}")
    Call<ExamDetail> getExam(
            @Path("exam_id") int examId
    );


    @POST("exam/{exam_id}/grade")
    Call<GradeResult> gradeExam(
            @Path("exam_id") int examId,
            @Query("user_id") int userId,
            @Body GradeRequest request
    );

    @GET("history")
    Call<java.util.List<HistoryItem>> getHistory(
            @Query("user_id") int userId
    );

    @GET("exam/{exam_id}/result")
    Call<GradeResultDto> getExamResult(
            @Path("exam_id") int examId
    );

    @POST("exam/{exam_id}/grade")
    Call<GradeResultDto> gradeExam(
            @Path("exam_id") int examId,
            @Query("user_id") int userId,
            @Body GradeRequestDto request
    );

    @POST("reset-password/")
    Call<GenericResponse> resetPassword(@Body ResetPasswordRequest request);



}
