package com.xoxoer.triviaquestion.ui.mainmenu

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xoxoer.lifemarklibrary.Lifemark
import com.xoxoer.triviaquestion.models.Questions
import com.xoxoer.triviaquestion.util.apisingleobserver.RxSingleHandler
import com.xoxoer.triviaquestion.viewmodels.ViewModelContract
import javax.inject.Inject

class QuestionTriviaViewModel @Inject constructor(
    application: Application,
    lifemark: Lifemark,
    private val questionTriviaRepository: QuestionTriviaRepository
) : ViewModel(), ViewModelContract {

    var amount = ObservableField(0)
    var category = ObservableField(0)
    var difficulty = ObservableField("")
    var type = ObservableField("")

    private val _questionTriviaSuccess = MutableLiveData<Questions>()
    val questionTriviaSuccess: LiveData<Questions>
        get() = _questionTriviaSuccess

    private val rxSingleHandler = RxSingleHandler(application, lifemark, this)
    override val isLoading = MutableLiveData<Boolean>()
    override val error = ObservableField<Boolean>()
    override val errorReason = ObservableField<String>()

    private fun onStart() {
        isLoading.value = true
    }

    private fun onFinish() {
        isLoading.value = false
    }

    fun retrieveQuestionTrivia() {
        questionTriviaRepository.retrieveQuestions(
            amount.get()!!,
            category.get()!!,
            difficulty.get()!!,
            type.get()!!,
            { onStart() },
            { onFinish() },
            rxSingleHandler.handler(_questionTriviaSuccess)
        )
    }

}