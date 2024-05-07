package com.mevi.data.repository.auth.api.firebase

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.auth
import com.mevi.data.repository.auth.api.UserApi
import com.mevi.domain.repository.auth.model.AuthenticationResult
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

    override suspend fun register(credentials: Pair<String, String>): AuthenticationResult = try {
        val authResult = firebaseAuth
            .createUserWithEmailAndPassword(credentials.first, credentials.second)
            .await()
        if (authResult.user != null) AuthenticationResult.SUCCESS else AuthenticationResult.ERROR_ACCOUNT_DOES_NO_EXIST
    } catch (e: Exception) {
        getErrorByException(AuthType.REGISTRATION, e)
    }

    override suspend fun login(credentials: Pair<String, String>): AuthenticationResult = try {
        val authResult = firebaseAuth
            .signInWithEmailAndPassword(credentials.first, credentials.second)
            .await()
        if (authResult.user != null) AuthenticationResult.SUCCESS else AuthenticationResult.ERROR_ACCOUNT_DOES_NO_EXIST
    } catch (e: Exception) {
        getErrorByException(AuthType.LOGIN, e)
    }

    override suspend fun logout() = try {
        firebaseAuth.signOut()
        AuthenticationResult.SUCCESS
    } catch (_: Exception) {
        AuthenticationResult.ERROR_UNEXPECTED
    }

    override val isAuthenticated: Boolean
        get() = firebaseAuth.currentUser != null


    private fun getErrorByException(authType: AuthType, authException: Exception): AuthenticationResult =
        when {
            // thrown if the user account corresponding to email does not exist or has been disabled
            authException is FirebaseAuthInvalidUserException -> AuthenticationResult.ERROR_ACCOUNT_DOES_NO_EXIST
            // or if the email or password is invalid
            authException is FirebaseAuthInvalidCredentialsException && authType == AuthType.LOGIN -> AuthenticationResult.ERROR_WRONG_EMAIL_OR_PASSWORD
            // thrown if the email address is malformed
            authException is FirebaseAuthInvalidCredentialsException && authType == AuthType.REGISTRATION -> AuthenticationResult.ERROR_INCORRECT_EMAIL
            // thrown if the password is not strong enough
            authException is FirebaseAuthWeakPasswordException -> AuthenticationResult.ERROR_WEAK_PASSWORD
            // thrown if there already exists an account with the given email address
            authException is FirebaseAuthUserCollisionException -> AuthenticationResult.ERROR_ACCOUNT_ALREADY_EXISTS
            else -> AuthenticationResult.ERROR_UNEXPECTED
        }

    /**
     * Authentication type
     */
    private enum class AuthType {
        LOGIN, REGISTRATION
    }
}