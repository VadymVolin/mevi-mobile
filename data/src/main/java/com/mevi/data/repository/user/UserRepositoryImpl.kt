package com.mevi.data.repository.user

import com.mevi.data.repository.user.api.firebase.FirebaseUserApi
import com.mevi.data.repository.user.api.model.ApiRequestStatus
import com.mevi.data.repository.user.api.model.ApiResult
import com.mevi.domain.repository.model.MeviError
import com.mevi.domain.repository.user.UserRepository
import com.mevi.domain.repository.user.model.MeviUser
import com.mevi.domain.repository.model.MeviResult

/**
 * Repository implementation of [UserRepository] associated with authentication processes and operations
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 4/5/24
 */
class UserRepositoryImpl(private val firebaseAuthApi: FirebaseUserApi) : UserRepository {
    override suspend fun registerByFirebase(credentials: Pair<String, String>): MeviResult<MeviUser> =
        when (val apiResult = firebaseAuthApi.register(credentials)) {
            is ApiResult.Success -> {
                MeviResult.Success(
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

            is ApiResult.Error -> MeviResult.Error(getErrorFromApiRequestStatus(apiResult.apiRequestStatus))
        }

    override suspend fun loginByFirebase(credentials: Pair<String, String>): MeviResult<MeviUser> =
        when (val apiResult = firebaseAuthApi.login(credentials)) {
            is ApiResult.Success -> {
                MeviResult.Success(
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

            is ApiResult.Error -> MeviResult.Error(getErrorFromApiRequestStatus(apiResult.apiRequestStatus))
        }

    override suspend fun logoutByFirebase(): MeviResult<Unit> =
        when (val apiResult = firebaseAuthApi.logout()) {
            is ApiResult.Success -> MeviResult.Success(Unit)
            is ApiResult.Error -> MeviResult.Error(getErrorFromApiRequestStatus(apiResult.apiRequestStatus))
        }

    override val isAuthenticated: Boolean
        get() = isAuthenticatedByFirebase

    private val isAuthenticatedByFirebase: Boolean
        get() = firebaseAuthApi.isAuthenticated

    private fun getErrorFromApiRequestStatus(apiRequestStatus: ApiRequestStatus): MeviError = when(apiRequestStatus) {
        ApiRequestStatus.SUCCESS, ApiRequestStatus.ERROR_UNEXPECTED -> MeviError.UserError.ERROR_UNEXPECTED
        ApiRequestStatus.ERROR_ACCOUNT_DOES_NO_EXIST -> MeviError.UserError.ERROR_ACCOUNT_DOES_NO_EXIST
        ApiRequestStatus.ERROR_ACCOUNT_ALREADY_EXISTS -> MeviError.UserError.ERROR_ACCOUNT_ALREADY_EXISTS
        ApiRequestStatus.ERROR_WEAK_PASSWORD -> MeviError.UserError.ERROR_WEAK_PASSWORD
        ApiRequestStatus.ERROR_WRONG_EMAIL_OR_PASSWORD -> MeviError.UserError.ERROR_WRONG_EMAIL_OR_PASSWORD
        ApiRequestStatus.ERROR_INCORRECT_EMAIL -> MeviError.UserError.ERROR_INCORRECT_EMAIL
    }
}