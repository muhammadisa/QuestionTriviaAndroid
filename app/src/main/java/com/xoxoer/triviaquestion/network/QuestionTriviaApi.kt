package com.xoxoer.triviaquestion.network

import com.xoxoer.triviaquestion.models.Questions
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface QuestionTriviaApi {
    @GET("/api.php")
    fun retrieveQuestion(
        @Query("amount") amount: Int,
        @Query("category") category: Int,
        @Query("difficulty") difficulty: String,
        @Query("type") type: String
    ): Single<Questions>
}