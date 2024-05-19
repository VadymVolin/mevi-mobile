package com.mevi.data.repository.user.api

import com.mevi.data.repository.user.api.model.UserDto

/**
 * Base interface for user api
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 4/5/24
 */
interface UserApi {

    /**
     * Creates new user account
     *
     * @param credentials user [Pair] credentials,
     * where [Pair.first] is an email or username
     * and [Pair.second] is a password
     */
    suspend fun register(credentials: Pair<String, String>): UserDto


    /**
     * Login to user account
     *
     * @param credentials user [Pair] credentials,
     * where [Pair.first] is an email or username
     * and [Pair.second] is a password
     */
    suspend fun login(credentials: Pair<String, String>): UserDto

    /**
     * Logs out user
     */
    suspend fun logout()

    /**
     * Returns if user logged in or not
     */
    val isAuthenticated: Boolean
}