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
    override val email: String? = null,
    override val phoneNumber: String? = null,
    override val provider: AuthenticationProvider,
    override val name: String? = null,
    override val isEmailVerified: Boolean = false,
    override val photoUrl: String? = null,
    override val password: String? = null,
    override val country: MeviCountry? = null,
    override val gender: MeviGender? = null
) : BaseUser
