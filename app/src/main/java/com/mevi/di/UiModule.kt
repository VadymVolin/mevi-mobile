package com.mevi.di

import com.mevi.ui.internet.NetworkManager
import com.mevi.ui.navigation.NavigationComponent
import com.mevi.ui.screens.authorization.AuthorizationViewModel
import com.mevi.ui.screens.loading.StartViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * DI module for UI module
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 2/29/24
 */
val uiModule = module {
    single { NetworkManager(androidContext()) }
    single { NavigationComponent() }
    viewModel { AuthorizationViewModel(get(), get(), get()) }
    viewModel { StartViewModel(get()) }
}