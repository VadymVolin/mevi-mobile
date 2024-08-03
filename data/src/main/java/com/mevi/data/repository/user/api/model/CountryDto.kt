package com.mevi.data.repository.user.api.model

import com.mevi.domain.repository.user.model.BaseCountry

/**
 * DTO model to store user country
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 3/8/24
 */
data class CountryDto(
    override val isoCode: String,
    override val countryName: String,
    override val flag: String
) : BaseCountry