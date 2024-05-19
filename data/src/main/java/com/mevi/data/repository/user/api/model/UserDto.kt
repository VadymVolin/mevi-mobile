package com.mevi.data.repository.user.api.model

import com.mevi.domain.repository.user.model.BaseUser

/**
 * DTO model to store user data
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 7/5/24
 */
data class UserDto(
    override val email: String?,
    override val phoneNumber: String?,
    override val tenantId: String?,
    override val displayName: String?,
    override val isEmailVerified: Boolean,
    override val photoUrl: String?
) : BaseUser