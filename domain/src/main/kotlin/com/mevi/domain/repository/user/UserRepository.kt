package com.mevi.domain.repository.user

import com.mevi.domain.repository.model.MeviResult
import com.mevi.domain.repository.user.model.MeviUser

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
    suspend fun registerByFirebase(credentials: Pair<String, String>): MeviResult<MeviUser>


    /**
     * Login to user account
     *
     * @param credentials user [Pair] credentials,
     * where [Pair.first] is an email or username
     * and [Pair.second] is a password
     */
    suspend fun loginByFirebase(credentials: Pair<String, String>): MeviResult<MeviUser>

    /**
     * Logs out user
     */
    suspend fun logoutByFirebase(): MeviResult<Unit>

    /**
     * Returns if user logged in or not
     */
    val isAuthenticated: Boolean
}