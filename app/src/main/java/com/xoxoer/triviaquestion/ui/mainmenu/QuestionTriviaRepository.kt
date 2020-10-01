package com.xoxoer.triviaquestion.ui.mainmenu

import com.xoxoer.triviaquestion.models.Questions
import com.xoxoer.triviaquestion.network.QuestionTriviaApi
import com.xoxoer.triviaquestion.util.apisingleobserver.ApiSingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class QuestionTriviaRepository @Inject constructor(
    private val questionTriviaApi: QuestionTriviaApi
) {
    fun retrieveQuestions(
        amount: Int,
        category: Int,
        difficulty: String,
        type: String,
        onStart: () -> Unit,
        onFinish: () -> Unit,
        handler: ApiSingleObserver<Questions>
    ) {
        questionTriviaApi.retrieveQuestion(amount, category, difficulty, type)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onStart() }
            .doOnTerminate { onFinish() }
            .doOnSuccess { }
            .subscribe(handler)
    }
}