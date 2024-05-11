package com.mevi.ui.screens.state

import androidx.compose.runtime.Stable
import com.mevi.domain.model.MeviError

/**
 * Holder for screen state
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 8/5/24
 * */
@Stable
data class UIScreenState<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val error: MeviError? = null
)