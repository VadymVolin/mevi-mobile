package com.mevi.domain.repository.model

/**
 * Class to represent the result of an action
 * [Success] or [Error]
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 7/5/24
 */
sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error<out T>(val error: T) : Result<T>()
}