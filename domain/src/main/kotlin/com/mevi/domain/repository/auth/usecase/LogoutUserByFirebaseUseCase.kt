package com.mevi.domain.repository.auth.usecase

import com.mevi.domain.repository.auth.UserRepository
import com.mevi.domain.repository.auth.model.AuthenticationResult

/**
 * Logs out user via Firebase
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 6/5/24
 */
class LogoutUserByFirebaseUseCase(private val userRepository: UserRepository) :
    BaseNetworkBasedUseCase<AuthenticationResult>() {
    fun logout() = execute { userRepository.logoutByFirebase() }
}