package com.mevi.data.repository.user.api.firebase

import android.net.Uri
import android.util.Log
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
import com.mevi.domain.repository.user.model.AuthenticationProvider
import com.mevi.domain.repository.user.usecase.model.RegisterUserModel
import kotlinx.coroutines.tasks.await

/**
 * Firebase implementation of [UserApi]
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 4/5/24
 */
class UserFirebaseApi : UserApi {

    companion object {
        private val TAG = UserFirebaseApi::class.java.name

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
    override suspend fun register(registerUserModel: RegisterUserModel): UserDto {
        val email: String = registerUserModel.email ?: throw FirebaseAuthInvalidUserException(
            INTERNAL_ERROR_CODE,
            "Email is null"
        )
        val password: String = registerUserModel.password ?: throw FirebaseAuthInvalidUserException(
            INTERNAL_ERROR_CODE,
            "Password is null"
        )
        val authResult = firebaseAuth
            .createUserWithEmailAndPassword(email, password)
            .await()
        val firebaseUser = authResult.user
        return firebaseUser?.let {
            UserDto(
                firebaseUser.email,
                firebaseUser.phoneNumber,
                AuthenticationProvider.CREDENTIALS,
                firebaseUser.displayName,
                firebaseUser.isEmailVerified,
                firebaseUser.photoUrl?.toString(),
                password
            )
        } ?: throw FirebaseAuthInvalidUserException(
            INTERNAL_ERROR_CODE,
            "User not found after registration"
        )
    }

    @Throws(FirebaseAuthInvalidUserException::class)
    override suspend fun login(credentials: Pair<String, String>): UserDto {
        val authResult = firebaseAuth
            .signInWithEmailAndPassword(credentials.first, credentials.second)
            .await()
        val firebaseUser = authResult.user
        return firebaseUser?.let {
            UserDto(
                firebaseUser.email,
                firebaseUser.phoneNumber,
                AuthenticationProvider.CREDENTIALS,
                firebaseUser.displayName,
                firebaseUser.isEmailVerified,
                firebaseUser.photoUrl?.toString(),
                credentials.second
            )
        } ?: throw FirebaseAuthInvalidUserException(
            INTERNAL_ERROR_CODE,
            "User not found after login"
        )
    }

    @Throws(Exception::class)
    override suspend fun loginWithGoogle(googleIdToken: String?): UserDto {
        val authCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
        val authResult = firebaseAuth.signInWithCredential(authCredential).await()

        val firebaseUser = authResult.user
        return firebaseUser?.let {
            UserDto(
                firebaseUser.email,
                firebaseUser.phoneNumber,
                AuthenticationProvider.GOOGLE,
                firebaseUser.displayName,
                firebaseUser.isEmailVerified,
                firebaseUser.photoUrl?.toString()
            )
        } ?: throw FirebaseAuthInvalidUserException(
            INTERNAL_ERROR_CODE,
            "User not found after login"
        )
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