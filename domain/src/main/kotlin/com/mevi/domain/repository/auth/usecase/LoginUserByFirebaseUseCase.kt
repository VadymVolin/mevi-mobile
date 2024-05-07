package com.mevi.domain.repository.auth.usecase

import com.mevi.domain.repository.auth.UserRepository
import com.mevi.domain.repository.model.Result

/**
 * Logins user via Firebase
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 6/5/24
 */
class LoginUserByFirebaseUseCase(private val userRepository: UserRepository) :
    BaseNetworkBasedUseCase<Result<Any>>() {
    fun login(credentials: Pair<String, String>) = execute { userRepository.loginByFirebase(credentials) }
}