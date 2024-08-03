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
     * Logins to user account
     *
     * @param credentials user [Pair] credentials,
     * where [Pair.first] is an email or username
     * and [Pair.second] is a password
     */
    suspend fun login(credentials: Pair<String, String>): UserDto

    /**
     * Logins with google account
     * @param googleIdToken googleIdToken value
     */
     suspend fun loginWithGoogle(googleIdToken: String?): UserDto

        /**
     * Logs out user
     */
    suspend fun logout()

    /**
     * Updates user profile
     *
     * @param name new user name, may be null
     * @param avatarUrl url of the new user avatar, may be null
     */
    suspend fun updateProfilePublicData(name: String?, avatarUrl: String?)

    /**
     * Updates user profile e-mail
     *
     * @param email new user email
     */
    suspend fun updateEmail(email: String)

    /**
     * Returns if user logged in or not
     */
    val isAuthenticated: Boolean
}