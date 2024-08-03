package com.mevi.domain.repository.user.usecase

import com.mevi.domain.model.MeviResult
import com.mevi.domain.repository.model.RepositoryResult
import com.mevi.domain.repository.user.UserRepository
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
    fun register(credentials: Pair<String, String>) = execute {
        when (val apiResult = userRepository.registerByFirebase(credentials)) {
            is RepositoryResult.Success -> {
                MeviResult.Success(
                    MeviUser(
                        apiResult.data.email,
                        apiResult.data.phoneNumber,
                        apiResult.data.providerId,
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