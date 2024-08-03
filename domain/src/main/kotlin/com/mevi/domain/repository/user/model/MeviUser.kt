package com.mevi.domain.repository.user.model

/**
 * Mevi User model class
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 8/5/24
 */
data class MeviUser(
    override val email: String?,
    override val phoneNumber: String?,
    override val providerId: String?,
    override val name: String?,
    override val isEmailVerified: Boolean,
    override val photoUrl: String?
) : BaseUser
