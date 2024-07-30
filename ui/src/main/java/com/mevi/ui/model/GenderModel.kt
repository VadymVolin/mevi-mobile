package com.mevi.ui.model

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.runtime.Stable

@Stable
data class GenderModel(
    val type: String,
    val icon: ImageVector
)