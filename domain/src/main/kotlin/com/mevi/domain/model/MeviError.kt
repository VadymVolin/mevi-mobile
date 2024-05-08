package com.mevi.domain.model

/**
 * General Mevi errors
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 5/5/24
 */
enum class MeviError {
    ERROR_UNEXPECTED,
    ERROR_ACCOUNT_DOES_NO_EXIST,
    ERROR_ACCOUNT_ALREADY_EXISTS,
    ERROR_WEAK_PASSWORD,
    ERROR_WRONG_EMAIL_OR_PASSWORD,
    ERROR_INCORRECT_EMAIL,
}