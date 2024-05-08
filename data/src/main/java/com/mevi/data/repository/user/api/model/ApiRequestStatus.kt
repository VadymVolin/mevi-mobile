package com.mevi.data.repository.user.api.model

/**
 * General API results
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 5/5/24
 */
enum class ApiRequestStatus {
    SUCCESS,
    ERROR_UNEXPECTED,
    // User related errors
    ERROR_ACCOUNT_DOES_NO_EXIST,
    ERROR_ACCOUNT_ALREADY_EXISTS,
    ERROR_WEAK_PASSWORD,
    ERROR_WRONG_EMAIL_OR_PASSWORD,
    ERROR_INCORRECT_EMAIL,
}