package com.xoxoer.triviaquestion.di

import com.xoxoer.triviaquestion.di.questiontrivia.QuestionTriviaModule
import com.xoxoer.triviaquestion.di.questiontrivia.QuestionTriviaViewModelModule
import com.xoxoer.triviaquestion.ui.mainmenu.MainMenuActivity
import com.xoxoer.triviaquestion.ui.mainmenu.QuestionTriviaViewModel
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
        modules = [
            QuestionTriviaViewModelModule::class,
            QuestionTriviaModule::class
        ]
    )
    abstract fun contributeMainMenuActivity(): MainMenuActivity

}