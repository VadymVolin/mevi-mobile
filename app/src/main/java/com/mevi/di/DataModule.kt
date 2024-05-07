package com.mevi.di

import com.mevi.data.repository.auth.UserRepositoryImpl
import com.mevi.data.repository.auth.api.firebase.FirebaseUserApi
import com.mevi.domain.repository.auth.UserRepository
import org.koin.dsl.module

/**
 * DI module for DATA module
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 2/29/24
 */
val dataModule = module {
    single<FirebaseUserApi> { FirebaseUserApi() }
    single<UserRepository> { UserRepositoryImpl(get()) }
}