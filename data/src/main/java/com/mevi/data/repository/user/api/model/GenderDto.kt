package com.mevi.data.repository.user.api.model

import com.mevi.domain.repository.user.model.BaseGender

/**
 * DTO model to store user gender
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 3/8/24
 */
data class GenderDto(
    override val type: String,
) : BaseGender