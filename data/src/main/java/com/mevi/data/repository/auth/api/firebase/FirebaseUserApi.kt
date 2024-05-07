package com.mevi.data.repository.auth.api.firebase

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.mevi.data.repository.auth.api.UserApi
import com.mevi.data.repository.auth.api.model.ApiRequestStatus
import com.mevi.data.repository.auth.api.model.ApiResult
import kotlinx.coroutines.tasks.await

/**
 * Firebase implementation of [UserApi]
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 4/5/24
 */
class FirebaseUserApi : UserApi {

    private val firebaseAuth: FirebaseAuth
        get() = Firebase.auth

    override suspend fun register(credentials: Pair<String, String>): ApiResult<FirebaseUser> = try {
        val authResult = firebaseAuth
            .createUserWithEmailAndPassword(credentials.first, credentials.second)
            .await()
        val firebaseUser = authResult.user
        if (firebaseUser != null) {
            ApiResult.Success(ApiRequestStatus.SUCCESS, firebaseUser)
        } else {
            ApiResult.Error(ApiRequestStatus.ERROR_ACCOUNT_DOES_NO_EXIST)
        }
    } catch (e: Exception) {
        ApiResult.Error(getErrorByException(ActionType.REGISTRATION, e))
    }

    override suspend fun login(credentials: Pair<String, String>): ApiResult<FirebaseUser> = try {
        val authResult = firebaseAuth
            .signInWithEmailAndPassword(credentials.first, credentials.second)
            .await()
        val firebaseUser: FirebaseUser? = authResult.user
        if (firebaseUser != null) {
            ApiResult.Success(ApiRequestStatus.SUCCESS, firebaseUser)
        } else {
            ApiResult.Error(ApiRequestStatus.ERROR_ACCOUNT_DOES_NO_EXIST)
        }
    } catch (e: Exception) {
        ApiResult.Error(getErrorByException(ActionType.LOGIN, e))
    }

    override suspend fun logout(): ApiResult<Unit> = try {
        ApiResult.Success(ApiRequestStatus.SUCCESS, firebaseAuth.signOut())
    } catch (_: Exception) {
        ApiResult.Error(ApiRequestStatus.ERROR_UNEXPECTED)
    }

    override val isAuthenticated: Boolean
        get() = firebaseAuth.currentUser != null

    private fun getErrorByException(actionType: ActionType, authException: Exception): ApiRequestStatus =
        when {
            // thrown if the user account corresponding to email does not exist or has been disabled
            authException is FirebaseAuthInvalidUserException -> ApiRequestStatus.ERROR_ACCOUNT_DOES_NO_EXIST
            // or if the email or password is invalid
            authException is FirebaseAuthInvalidCredentialsException && actionType == ActionType.LOGIN -> ApiRequestStatus.ERROR_WRONG_EMAIL_OR_PASSWORD
            // thrown if the email address is malformed
            authException is FirebaseAuthInvalidCredentialsException && actionType == ActionType.REGISTRATION -> ApiRequestStatus.ERROR_INCORRECT_EMAIL
            // thrown if the password is not strong enough
            authException is FirebaseAuthWeakPasswordException -> ApiRequestStatus.ERROR_WEAK_PASSWORD
            // thrown if there already exists an account with the given email address
            authException is FirebaseAuthUserCollisionException -> ApiRequestStatus.ERROR_ACCOUNT_ALREADY_EXISTS
            else -> ApiRequestStatus.ERROR_UNEXPECTED
        }

    /**
     * Firebase action type
     */
    private enum class ActionType {
        LOGIN, REGISTRATION
    }
}