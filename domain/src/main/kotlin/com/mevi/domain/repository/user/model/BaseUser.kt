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
    val tenantId: String?
    val name: String?
    val isEmailVerified: Boolean
    val photoUrl: String?
}