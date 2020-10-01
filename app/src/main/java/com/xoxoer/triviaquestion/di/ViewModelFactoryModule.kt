package com.xoxoer.triviaquestion.di

import androidx.lifecycle.ViewModelProvider
import com.xoxoer.triviaquestion.viewmodels.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(
        viewModelProviderFactory: ViewModelProviderFactory
    ): ViewModelProvider.Factory
}