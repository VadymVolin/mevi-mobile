package com.mevi.domain.repository.user.usecase

import com.mevi.domain.repository.user.UserRepository
import com.mevi.domain.repository.model.MeviResult
import com.mevi.domain.repository.user.model.MeviUser

/**
 * Registers user via Firebase
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 6/5/24
 */
class RegisterUserByFirebaseUseCase(private val userRepository: UserRepository) :
    BaseNetworkBasedUseCase<MeviResult<MeviUser>>() {
    fun register(credentials: Pair<String, String>) = execute { userRepository.registerByFirebase(credentials) }
}