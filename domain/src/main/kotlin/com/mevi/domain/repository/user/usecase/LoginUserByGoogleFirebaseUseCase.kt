package com.mevi.domain.repository.user.usecase

import com.mevi.domain.model.MeviResult
import com.mevi.domain.repository.model.RepositoryResult
import com.mevi.domain.repository.user.UserRepository
import com.mevi.domain.repository.user.model.MeviUser

/**
 * Logins user via Google + Firebase
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 3/8/24
 */
class LoginUserByGoogleFirebaseUseCase(private val userRepository: UserRepository) :
    BaseNetworkBasedUseCase<MeviResult<MeviUser>>() {
    fun login(googleIdToken: String?) = execute {
        when (val apiResult = userRepository.loginByGoogleFirebase(googleIdToken)) {
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