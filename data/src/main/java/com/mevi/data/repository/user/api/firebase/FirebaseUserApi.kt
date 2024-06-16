package com.mevi.data.repository.user.api.firebase

import android.net.Uri
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.mevi.data.repository.user.api.UserApi
import com.mevi.data.repository.user.api.model.UserDto
import kotlinx.coroutines.tasks.await
import java.io.IOException

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
        private val TAG = FirebaseUserApi::class.java.name

        const val INTERNAL_ERROR_CODE = "USER_NOT_FOUND"
    }

    private val firebaseAuth: FirebaseAuth
        get() = Firebase.auth

    private val firebaseAuthStateListener = AuthStateListener {
        Log.d(TAG, "Authentication state has been changed \nold: [$firebaseAuth] \nnew: [$it]")
    }

    init {
        firebaseAuth.addAuthStateListener(firebaseAuthStateListener)
    }

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

    @Throws(FirebaseAuthInvalidUserException::class)
    override suspend fun updateProfile(name: String?, avatarUrl: String?) {
        firebaseAuth.currentUser?.updateProfile(userProfileChangeRequest {
            this.displayName = name
            this.photoUri = Uri.parse(avatarUrl)
        })?.await() ?: throw FirebaseAuthInvalidUserException(INTERNAL_ERROR_CODE, "User is not authorized")
    }

    override val isAuthenticated: Boolean
        get() = firebaseAuth.currentUser != null
}