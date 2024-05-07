package com.mevi.domain.repository.auth.usecase

import com.mevi.domain.repository.auth.UserRepository
import com.mevi.domain.repository.model.Result

/**
 * Registers user via Firebase
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 6/5/24
 */
class RegisterUserByFirebaseUseCase(private val userRepository: UserRepository) :
    BaseNetworkBasedUseCase<Result<Any>>() {
    fun register(credentials: Pair<String, String>) = execute { userRepository.registerByFirebase(credentials) }
}