package com.mevi.domain.repository.model

/**
 * Class to represent the result of an API action
 * [Success] or [Error]
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 7/5/24
 */
sealed class RepositoryResult<out R> {
    data class Success<out T>(val data: T) : RepositoryResult<T>()
    data class Error(val exception: RepositoryException) : RepositoryResult<Nothing>()
}