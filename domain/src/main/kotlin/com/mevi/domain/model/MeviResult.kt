package com.mevi.domain.model

/**
 * Class to represent the result of a Mevi action
 * [Success] or [Error]
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 7/5/24
 */
sealed class MeviResult<out R> {
    data class Success<out T>(val data: T) : MeviResult<T>()
    data class Error(val error: MeviError) : MeviResult<Nothing>()
}