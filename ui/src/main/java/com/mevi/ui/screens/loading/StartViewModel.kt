package com.mevi.ui.screens.loading

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mevi.domain.model.MeviResult
import com.mevi.domain.repository.user.usecase.CheckUserAuthorizationByFirebaseUseCase
import com.mevi.ui.screens.state.UIScreenState
import kotlinx.coroutines.launch

/**
 * ViewModel for [StartScreen]
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 12/5/24
 * */
class StartViewModel(
    private val checkUserAuthorizationByFirebaseUseCase: CheckUserAuthorizationByFirebaseUseCase
) : ViewModel() {

    /**
     * Account state
     */
    var accountState by mutableStateOf(UIScreenState<Boolean>(true, null, null))
        private set

    fun checkUserAuthorization() {
        viewModelScope.launch {
            checkUserAuthorizationByFirebaseUseCase.check()
                .collect {
                    accountState = when (it) {
                        is MeviResult.Success -> UIScreenState(false, it.data, null)
                        is MeviResult.Error -> UIScreenState(false, null, it.error)
                    }
                }
        }
    }
}