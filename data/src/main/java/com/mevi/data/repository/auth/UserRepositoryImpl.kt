package com.mevi.data.repository.auth

import com.mevi.data.repository.auth.api.firebase.FirebaseUserApi
import com.mevi.data.repository.auth.api.model.ApiRequestStatus
import com.mevi.data.repository.auth.api.model.ApiResult
import com.mevi.domain.repository.auth.UserRepository
import com.mevi.domain.repository.auth.model.MeviUser
import com.mevi.domain.repository.auth.model.MeviUserError
import com.mevi.domain.repository.model.Result

/**
 * Repository implementation of [UserRepository] associated with authentication processes and operations
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 4/5/24
 */
class UserRepositoryImpl(private val firebaseAuthApi: FirebaseUserApi) : UserRepository {
    override suspend fun registerByFirebase(credentials: Pair<String, String>): Result<Any> =
        when (val apiResult = firebaseAuthApi.register(credentials)) {
            is ApiResult.Success -> {
                Result.Success(
                    MeviUser(
                        apiResult.data.email,
                        apiResult.data.phoneNumber,
                        apiResult.data.tenantId,
                        apiResult.data.displayName,
                        apiResult.data.isEmailVerified,
                        apiResult.data.photoUrl?.toString()
                    )
                )
            }

            is ApiResult.Error -> Result.Error(getErrorFromApiRequestStatus(apiResult.apiRequestStatus))
        }

    override suspend fun loginByFirebase(credentials: Pair<String, String>): Result<Any> =
        when (val apiResult = firebaseAuthApi.login(credentials)) {
            is ApiResult.Success -> {
                Result.Success(
                    MeviUser(
                        apiResult.data.email,
                        apiResult.data.phoneNumber,
                        apiResult.data.tenantId,
                        apiResult.data.displayName,
                        apiResult.data.isEmailVerified,
                        apiResult.data.photoUrl?.toString()
                    )
                )
            }

            is ApiResult.Error -> Result.Error(getErrorFromApiRequestStatus(apiResult.apiRequestStatus))
        }

    override suspend fun logoutByFirebase(): Result<Any> =
        when (val apiResult = firebaseAuthApi.logout()) {
            is ApiResult.Success -> Result.Success(Unit)
            is ApiResult.Error -> Result.Error(getErrorFromApiRequestStatus(apiResult.apiRequestStatus))
        }

    override val isAuthenticated: Boolean
        get() = isAuthenticatedByFirebase

    private val isAuthenticatedByFirebase: Boolean
        get() = firebaseAuthApi.isAuthenticated

    private fun getErrorFromApiRequestStatus(apiRequestStatus: ApiRequestStatus) = when(apiRequestStatus) {
        ApiRequestStatus.SUCCESS, ApiRequestStatus.ERROR_UNEXPECTED -> MeviUserError.ERROR_UNEXPECTED
        ApiRequestStatus.ERROR_ACCOUNT_DOES_NO_EXIST -> MeviUserError.ERROR_ACCOUNT_DOES_NO_EXIST
        ApiRequestStatus.ERROR_ACCOUNT_ALREADY_EXISTS -> MeviUserError.ERROR_ACCOUNT_ALREADY_EXISTS
        ApiRequestStatus.ERROR_WEAK_PASSWORD -> MeviUserError.ERROR_WEAK_PASSWORD
        ApiRequestStatus.ERROR_WRONG_EMAIL_OR_PASSWORD -> MeviUserError.ERROR_WRONG_EMAIL_OR_PASSWORD
        ApiRequestStatus.ERROR_INCORRECT_EMAIL -> MeviUserError.ERROR_INCORRECT_EMAIL
    }
}