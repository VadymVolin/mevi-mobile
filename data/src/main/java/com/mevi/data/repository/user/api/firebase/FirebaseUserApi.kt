package com.mevi.data.repository.user.api.firebase

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.auth
import com.mevi.data.repository.user.api.UserApi
import com.mevi.data.repository.user.api.model.UserDto
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

    companion object {
        const val INTERNAL_ERROR_CODE = "USER_NOT_FOUND"
    }

    private val firebaseAuth: FirebaseAuth
        get() = Firebase.auth

    override suspend fun register(credentials: Pair<String, String>): UserDto {
        val authResult = firebaseAuth
            .createUserWithEmailAndPassword(credentials.first, credentials.second)
            .await()
        val firebaseUser = authResult.user
        if (firebaseUser != null) {
            return UserDto(
                firebaseUser.email,
                firebaseUser.phoneNumber,
                firebaseUser.tenantId,
                firebaseUser.displayName,
                firebaseUser.isEmailVerified,
                firebaseUser.photoUrl?.toString()
            )
        } else {
            throw FirebaseAuthInvalidUserException(
                INTERNAL_ERROR_CODE,
                "User not found after registration"
            )
        }
    }

    override suspend fun login(credentials: Pair<String, String>): UserDto {
        val authResult = firebaseAuth
            .signInWithEmailAndPassword(credentials.first, credentials.second)
            .await()
        val firebaseUser = authResult.user
        if (firebaseUser != null) {
            return UserDto(
                firebaseUser.email,
                firebaseUser.phoneNumber,
                firebaseUser.tenantId,
                firebaseUser.displayName,
                firebaseUser.isEmailVerified,
                firebaseUser.photoUrl?.toString()
            )
        } else {
            throw FirebaseAuthInvalidUserException(
                INTERNAL_ERROR_CODE,
                "User not found after login"
            )
        }
    }

    override suspend fun logout() = firebaseAuth.signOut()

    override val isAuthenticated: Boolean
        get() = firebaseAuth.currentUser != null
}