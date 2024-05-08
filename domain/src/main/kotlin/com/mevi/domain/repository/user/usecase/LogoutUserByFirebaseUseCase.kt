package com.mevi.domain.repository.user.usecase

import com.mevi.domain.repository.user.UserRepository
import com.mevi.domain.repository.model.MeviResult

/**
 * Logs out user via Firebase
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 6/5/24
 */
class LogoutUserByFirebaseUseCase(private val userRepository: UserRepository) :
    BaseNetworkBasedUseCase<MeviResult<Unit>>() {
    fun logout() = execute { userRepository.logoutByFirebase() }
}