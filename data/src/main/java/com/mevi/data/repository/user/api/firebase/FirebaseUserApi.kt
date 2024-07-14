package com.mevi.data.repository.user.api.firebase

import android.net.Uri
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.mevi.data.BuildConfig
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
        private val TAG = FirebaseUserApi::class.java.name

        const val INTERNAL_ERROR_CODE = "USER_NOT_FOUND"
    }

    private val firebaseAuth: FirebaseAuth
        get() = Firebase.auth

    private val firebaseAuthStateListener = AuthStateListener {
        Log.d(TAG, "Authentication state has been changed, isAuthenticated: [${isAuthenticated}]")
    }

    init {
        firebaseAuth.addAuthStateListener(firebaseAuthStateListener)
    }

    @Throws(FirebaseAuthInvalidUserException::class)
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

    @Throws(FirebaseAuthInvalidUserException::class)
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

    @Throws(FirebaseAuthInvalidUserException::class)
    override suspend fun loginWithGoogle(googleTokenId: String): UserDto {
        val credential = GoogleAuthProvider.getCredential(googleTokenId, null)
        val authResult = firebaseAuth.signInWithCredential(credential).await()
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

    @Throws(FirebaseAuthInvalidUserException::class)
    override suspend fun updateProfilePublicData(name: String?, avatarUrl: String?) {
        firebaseAuth.currentUser?.updateProfile(userProfileChangeRequest {
            this.displayName = name
            this.photoUri = Uri.parse(avatarUrl)
        })?.await() ?: throw FirebaseAuthInvalidUserException(
            INTERNAL_ERROR_CODE,
            "User is not authorized"
        )
    }

    @Throws(FirebaseAuthInvalidUserException::class)
    override suspend fun updateEmail(email: String) {
        val actionCodeSettings = ActionCodeSettings.newBuilder()
            .setAndroidPackageName(BuildConfig.APP_PACKAGE_NAME, false, "1.0")
            .setIOSBundleId(null)
            .setUrl(BuildConfig.APP_URL)
            .build()
        firebaseAuth.currentUser
            ?.verifyBeforeUpdateEmail(email, actionCodeSettings)
            ?.await() ?: throw FirebaseAuthInvalidUserException(
            INTERNAL_ERROR_CODE,
            "User is not authorized"
        )
    }

    override suspend fun logout() = firebaseAuth.signOut()

    override val isAuthenticated: Boolean
        get() = firebaseAuth.currentUser != null
}