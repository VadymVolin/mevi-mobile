package com.mevi.data.repository.user.api.model

import com.mevi.domain.repository.user.model.AuthenticationProvider
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
    override val email: String? = null,
    override val phoneNumber: String? = null,
    override val provider: AuthenticationProvider,
    override val name: String? = null,
    override val isEmailVerified: Boolean = false,
    override val photoUrl: String? = null,
    override val password: String? = null,
    override val country: CountryDto? = null,
    override val gender: GenderDto? = null
) : BaseUser