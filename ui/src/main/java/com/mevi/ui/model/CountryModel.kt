package com.mevi.ui.model

import androidx.compose.runtime.Stable

@Stable
data class CountryModel(
    val isoCode: String,
    val countryName: String,
    val flag: String
)