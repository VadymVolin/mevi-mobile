package com.mevi.di

import com.mevi.ui.translations.TextMatcher
import com.mevi.ui.internet.NetworkManager
import com.mevi.ui.navigation.NavigationComponent
import com.mevi.ui.startup.standard.OnboardingChaneHandler
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import java.util.Locale

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
    single { TextMatcher(Locale.US, emptyMap()) }
    single { OnboardingChaneHandler(get(), get()) }
}