package com.xoxoer.triviaquestion.di.questiontrivia

import androidx.lifecycle.ViewModel
import com.xoxoer.gitpocket.di.ViewModelKey
import com.xoxoer.triviaquestion.ui.mainmenu.QuestionTriviaViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class QuestionTriviaViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(QuestionTriviaViewModel::class)
    abstract fun bindQuestionTriviaViewModel(
        questionTriviaViewModel: QuestionTriviaViewModel
    ): ViewModel
}