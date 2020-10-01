package com.xoxoer.triviaquestion.di.questiontrivia

import com.xoxoer.triviaquestion.network.QuestionTriviaApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object QuestionTriviaModule {
    @Provides
    @JvmStatic
    fun provideQuestionTriviaApi(retrofit: Retrofit): QuestionTriviaApi {
        return retrofit.create(QuestionTriviaApi::class.java)
    }
}