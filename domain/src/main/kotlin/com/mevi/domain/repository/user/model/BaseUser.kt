package com.mevi.domain.repository.user.model

/**
 * Base class for user model
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 8/5/24
 */
interface BaseUser {
    val email: String?
    val phoneNumber: String?
    val provider: AuthenticationProvider
    val name: String?
    val isEmailVerified: Boolean
    val photoUrl: String?
    val password: String?
    val country: BaseCountry?
    val gender: BaseGender?
}