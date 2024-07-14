package com.mevi.domain.repository.user.usecase

import com.mevi.domain.model.MeviResult
import com.mevi.domain.repository.model.RepositoryResult
import com.mevi.domain.repository.user.UserRepository
import com.mevi.domain.repository.user.model.MeviUser

/**
 * Updates user email via Firebase
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 7/6/24
 */
class UpdateUserEmailByFirebaseUseCase(private val userRepository: UserRepository) :
    BaseNetworkBasedUseCase<MeviResult<Unit>>() {

    fun update(email: String) = execute {
        when (val apiResult = userRepository.updateProfileEmailByFirebase(email)) {
            is RepositoryResult.Success -> {
                MeviResult.Success(apiResult.data)
            }

            is RepositoryResult.Error -> MeviResult.Error(getErrorByException(apiResult.exception))
        }
    }
}