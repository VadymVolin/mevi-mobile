package com.mevi.di

import com.mevi.domain.repository.user.usecase.CheckUserAuthorizationByFirebaseUseCase
import com.mevi.domain.repository.user.usecase.LoginUserByFirebaseUseCase
import com.mevi.domain.repository.user.usecase.LoginUserByGoogleFirebaseUseCase
import com.mevi.domain.repository.user.usecase.LogoutUserByFirebaseUseCase
import com.mevi.domain.repository.user.usecase.RegisterUserByFirebaseUseCase
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
    factory { LoginUserByGoogleFirebaseUseCase(get()) }
    factory { LoginUserByFirebaseUseCase(get()) }
    factory { RegisterUserByFirebaseUseCase(get()) }
    factory { LogoutUserByFirebaseUseCase(get()) }
    factory { CheckUserAuthorizationByFirebaseUseCase(get()) }
}