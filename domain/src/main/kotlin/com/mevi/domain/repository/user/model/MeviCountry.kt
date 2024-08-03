package com.mevi.domain.repository.user.model

data class MeviCountry(
    override val isoCode: String,
    override val countryName: String,
    override val flag: String
) : BaseCountry