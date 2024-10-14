package com.polipost.plus.di

import com.polipost.plus.presentation.home.viewmodel.HomeActivityViewModel
import com.polipost.plus.presentation.home.viewmodel.HomeChildFragmentViewModel
import com.polipost.plus.presentation.home.viewmodel.HomeFragmentViewModel
import com.polipost.plus.presentation.splash.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { HomeActivityViewModel(get()) }
    viewModel { HomeFragmentViewModel(get()) }
    viewModel { HomeChildFragmentViewModel(get()) }
}