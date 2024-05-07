package com.mevi.data.repository.auth.api.model

/**
 * General holder for API request result
 */
sealed class ApiResult<out R> {
    data class Success<out T>(val apiRequestStatus: ApiRequestStatus, val data: T) : ApiResult<T>()
    data class Error(val apiRequestStatus: ApiRequestStatus) : ApiResult<Nothing>()
}