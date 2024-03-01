package com.mevi.di

import com.mevi.ui.internet.NetworkManager
import org.koin.android.ext.koin.androidContext
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
}