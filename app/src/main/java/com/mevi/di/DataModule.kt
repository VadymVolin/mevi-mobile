package com.mevi.di

import com.mevi.data.repository.user.UserRepositoryImpl
import com.mevi.data.repository.user.api.UserApi
import com.mevi.data.repository.user.api.firebase.FirebaseUserApi
import com.mevi.domain.repository.user.UserRepository
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
    single<UserApi> { FirebaseUserApi() }
    single<UserRepository> { UserRepositoryImpl(get()) }
}