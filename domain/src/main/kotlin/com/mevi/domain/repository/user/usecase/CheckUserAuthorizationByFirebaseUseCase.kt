package com.mevi.domain.repository.user.usecase

import com.mevi.domain.repository.user.UserRepository
import com.mevi.domain.model.MeviResult
import com.mevi.domain.repository.model.RepositoryResult

/**
 * Check user authorization status via Firebase
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 12/5/24
 */
class CheckUserAuthorizationByFirebaseUseCase(private val userRepository: UserRepository) :
    BaseNetworkBasedUseCase<MeviResult<Boolean>>() {
    fun check() = execute {
        MeviResult.Success(userRepository.isAuthenticated)
    }
}