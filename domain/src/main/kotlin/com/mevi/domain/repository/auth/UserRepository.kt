package com.mevi.domain.repository.auth

import com.mevi.domain.repository.auth.model.AuthenticationResult

/**
 * Repository associated with authentication processes and operations
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 4/5/24
 * */
interface UserRepository {

    /**
     * Creates new user account
     *
     * @param credentials user [Pair] credentials,
     * where [Pair.first] is an email or username
     * and [Pair.second] is a password
     */
    suspend fun registerByFirebase(credentials: Pair<String, String>): AuthenticationResult


    /**
     * Login to user account
     *
     * @param credentials user [Pair] credentials,
     * where [Pair.first] is an email or username
     * and [Pair.second] is a password
     */
    suspend fun loginByFirebase(credentials: Pair<String, String>): AuthenticationResult

    /**
     * Logs out user
     */
    suspend fun logoutByFirebase(): AuthenticationResult

    /**
     * Returns if user logged in or not
     */
    val isAuthenticated: Boolean
}