package com.mevi.data.repository.auth

import com.mevi.data.repository.auth.api.firebase.FirebaseUserApi
import com.mevi.domain.repository.auth.UserRepository
import com.mevi.domain.repository.auth.model.AuthenticationResult

/**
 * Repository implementation of [UserRepository] associated with authentication processes and operations
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 4/5/24
 */
class UserRepositoryImpl(private val firebaseAuthApi: FirebaseUserApi) : UserRepository {
    override suspend fun registerByFirebase(credentials: Pair<String, String>): AuthenticationResult =
        firebaseAuthApi.register(credentials)

    override suspend fun loginByFirebase(credentials: Pair<String, String>): AuthenticationResult =
        firebaseAuthApi.login(credentials)

    override suspend fun logoutByFirebase(): AuthenticationResult = firebaseAuthApi.logout()

    override val isAuthenticated: Boolean
        get() = isAuthenticatedByFirebase

    private val isAuthenticatedByFirebase: Boolean
        get() = firebaseAuthApi.isAuthenticated
}