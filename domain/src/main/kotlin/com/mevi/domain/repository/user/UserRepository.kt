package com.mevi.domain.repository.user

import com.mevi.domain.repository.model.RepositoryResult
import com.mevi.domain.repository.user.model.BaseUser

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
    suspend fun registerByFirebase(credentials: Pair<String, String>): RepositoryResult<BaseUser>


    /**
     * Login to user account
     *
     * @param credentials user [Pair] credentials,
     * where [Pair.first] is an email or username
     * and [Pair.second] is a password
     */
    suspend fun loginByFirebase(credentials: Pair<String, String>): RepositoryResult<BaseUser>

    /**
     * Logs out user
     */
    suspend fun logoutByFirebase(): RepositoryResult<Unit>

    /**
     * Returns if user logged in or not
     */
    val isAuthenticated: Boolean

    val baseUser: BaseUser?
}