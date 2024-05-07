package com.mevi.di

import com.mevi.domain.repository.auth.usecase.LoginUserByFirebaseUseCase
import com.mevi.domain.repository.auth.usecase.LogoutUserByFirebaseUseCase
import com.mevi.domain.repository.auth.usecase.RegisterUserByFirebaseUseCase
import org.koin.dsl.module

/**
 * DI module for DOMAIN module
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 2/29/24
 */
val domainModule = module {
    factory { LoginUserByFirebaseUseCase(get()) }
    factory { RegisterUserByFirebaseUseCase(get()) }
    factory { LogoutUserByFirebaseUseCase(get()) }
}