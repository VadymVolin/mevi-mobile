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
     * Login to user account with Google + Firebase
     */
    suspend fun loginByGoogleFirebase(googleIdToken: String?): RepositoryResult<BaseUser>

    /**
     * Logs out user
     */
    suspend fun logoutByFirebase(): RepositoryResult<Unit>

    /**
     * Updates user profile
     *
     * @param name new user name, may be null, if null the old name will be used
     * @param avatarUrl url of the new user avatar, may be null
     */
    suspend fun updateProfileByFirebase(name: String?, avatarUrl: String?): RepositoryResult<Unit>

    /**
     * Updates user profile e-mail
     *
     * @param email new user email
     */
    suspend fun updateProfileEmailByFirebase(email: String): RepositoryResult<Unit>

    /**
     * Returns if user logged in or not
     */
    val isAuthenticated: Boolean

    val baseUser: BaseUser?
}