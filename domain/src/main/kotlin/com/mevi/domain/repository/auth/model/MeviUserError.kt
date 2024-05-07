package com.mevi.domain.repository.auth.model

/**
 * General User API errors
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 5/5/24
 */
enum class MeviUserError {
    ERROR_UNEXPECTED,
    ERROR_ACCOUNT_DOES_NO_EXIST,
    ERROR_ACCOUNT_ALREADY_EXISTS,
    ERROR_WEAK_PASSWORD,
    ERROR_WRONG_EMAIL_OR_PASSWORD,
    ERROR_INCORRECT_EMAIL,
}