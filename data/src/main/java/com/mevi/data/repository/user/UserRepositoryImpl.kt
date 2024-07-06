package com.mevi.data.repository.user

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.mevi.data.repository.user.api.UserApi
import com.mevi.data.repository.user.api.model.AccountDataDto
import com.mevi.data.repository.user.api.model.UserDto
import com.mevi.domain.repository.model.RepositoryException
import com.mevi.domain.repository.model.RepositoryResult
import com.mevi.domain.repository.user.UserRepository

/**
 * Repository implementation of [UserRepository] associated with authentication processes and operations
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 4/5/24
 */
class UserRepositoryImpl(private val firebaseAuthApi: UserApi) : UserRepository {

    private val accountData: AccountDataDto = AccountDataDto(null)

    override val baseUser: UserDto?
        get() = accountData.userDto

    override suspend fun registerByFirebase(credentials: Pair<String, String>): RepositoryResult<UserDto> =
        try {
            val user = firebaseAuthApi.register(credentials)
            accountData.userDto = user
            RepositoryResult.Success(user)
        } catch (e: Exception) {
            RepositoryResult.Error(getRepositoryException(e, ActionType.REGISTRATION))
        }

    override suspend fun loginByFirebase(credentials: Pair<String, String>): RepositoryResult<UserDto> =
        try {
            val user = firebaseAuthApi.login(credentials)
            accountData.userDto = user
            RepositoryResult.Success(user)
        } catch (e: Exception) {
            RepositoryResult.Error(getRepositoryException(e, ActionType.LOGIN))
        }

    override suspend fun logoutByFirebase(): RepositoryResult<Unit> = try {
        val success = firebaseAuthApi.logout()
        cleanUserData()
        RepositoryResult.Success(success)
    } catch (e: Exception) {
        RepositoryResult.Error(getRepositoryException(e))
    }

    override suspend fun updateProfileByFirebase(
        name: String?,
        avatarUrl: String?
    ): RepositoryResult<Unit> = try {
        RepositoryResult.Success(firebaseAuthApi.updateProfilePublicData(name ?: accountData.userDto?.name, avatarUrl))
    } catch (e: Exception) {
        RepositoryResult.Error(getRepositoryException(e))
    }

    override suspend fun updateProfileEmailByFirebase(
        email: String
    ): RepositoryResult<Unit> = try {
        RepositoryResult.Success(firebaseAuthApi.updateEmail(email))
    } catch (e: Exception) {
        RepositoryResult.Error(getRepositoryException(e))
    }

    override val isAuthenticated: Boolean
        get() = isAuthenticatedByFirebase

    private val isAuthenticatedByFirebase: Boolean
        get() = firebaseAuthApi.isAuthenticated

    private fun cleanUserData() {
        accountData.userDto = null
    }

    private fun getRepositoryException(
        authException: Exception,
        actionType: ActionType? = null
    ): RepositoryException =
        when {
            // thrown if the user account corresponding to email does not exist or has been disabled
            authException is FirebaseAuthInvalidUserException -> RepositoryException.AuthInvalidUserException(
                authException.message.toString()
            )
            // or if the email or password is invalid
            authException is FirebaseAuthInvalidCredentialsException && actionType == ActionType.LOGIN -> RepositoryException.AuthWrongEmailOrPasswordException(
                authException.message.toString()
            )
            // thrown if the email address is malformed
            authException is FirebaseAuthInvalidCredentialsException && actionType == ActionType.REGISTRATION -> RepositoryException.AuthInvalidEmailException(
                authException.message.toString()
            )
            // thrown if the password is not strong enough
            authException is FirebaseAuthWeakPasswordException -> RepositoryException.AuthWeakPasswordException(
                authException.message.toString()
            )
            // thrown if there already exists an account with the given email address
            authException is FirebaseAuthUserCollisionException -> RepositoryException.AuthUserAccountAlreadyExistsException(
                authException.message.toString()
            )

            else -> RepositoryException.UnexpectedError(authException.message.toString())
        }


    /**
     * Firebase action type
     */
    private enum class ActionType {
        LOGIN, REGISTRATION
    }
}