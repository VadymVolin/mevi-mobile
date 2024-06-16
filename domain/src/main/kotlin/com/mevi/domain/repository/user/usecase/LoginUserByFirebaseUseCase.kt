package com.mevi.domain.repository.user.usecase

import com.mevi.domain.model.MeviResult
import com.mevi.domain.repository.model.RepositoryResult
import com.mevi.domain.repository.user.UserRepository
import com.mevi.domain.repository.user.model.MeviUser

/**
 * Logins user via Firebase
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 6/5/24
 */
class LoginUserByFirebaseUseCase(private val userRepository: UserRepository) :
    BaseNetworkBasedUseCase<MeviResult<MeviUser>>() {
    fun login(credentials: Pair<String, String>) = execute {
        when (val apiResult = userRepository.loginByFirebase(credentials)) {
            is RepositoryResult.Success -> {
                MeviResult.Success(
                    MeviUser(
                        apiResult.data.email,
                        apiResult.data.phoneNumber,
                        apiResult.data.tenantId,
                        apiResult.data.name,
                        apiResult.data.isEmailVerified,
                        apiResult.data.photoUrl
                    )
                )
            }

            is RepositoryResult.Error -> MeviResult.Error(getErrorByException(apiResult.exception))
        }
    }
}