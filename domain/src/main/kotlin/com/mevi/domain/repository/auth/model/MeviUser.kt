package com.mevi.domain.repository.auth.model

/**
 * Base holder for app user data
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 7/5/24
 */
data class MeviUser(
    val email: String?,
    val phoneNumber: String?,
    val tenantId: String?,
    val displayName: String?,
    val isEmailVerified: Boolean,
    val photoUrl: String?
)
