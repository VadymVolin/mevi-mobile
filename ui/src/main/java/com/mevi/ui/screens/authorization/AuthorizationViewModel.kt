package com.mevi.ui.screens.authorization

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mevi.domain.model.MeviResult
import com.mevi.domain.repository.user.model.MeviUser
import com.mevi.domain.repository.user.usecase.LoginUserByFirebaseUseCase
import com.mevi.domain.repository.user.usecase.RegisterUserByFirebaseUseCase
import com.mevi.ui.screens.state.UIScreenState
import kotlinx.coroutines.launch

/**
 * ViewModel for [AuthorizationScreen]
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 8/5/24
 * */
class AuthorizationViewModel(
    private val registerUserByFirebaseUseCase: RegisterUserByFirebaseUseCase,
    private val loginUserByFirebaseUseCase: LoginUserByFirebaseUseCase,
) : ViewModel() {

    /**
     * Registration state
     */
    val registrationState = mutableStateOf(UIScreenState<MeviUser>(false, null, null))

    /**
     * Login state
     */
    var loginState = mutableStateOf(UIScreenState<MeviUser>(false, null, null))

    fun register(credentials: Pair<String, String>) {
        registrationState.value = UIScreenState(true, null, null)
        viewModelScope.launch {
            registerUserByFirebaseUseCase.register(credentials)
                .collect {
                    registrationState.value = when (it) {
                        is MeviResult.Success -> UIScreenState(false, it.data, null)
                        is MeviResult.Error -> UIScreenState(false, null, it.error)
                    }
                }
        }
    }

    fun login(credentials: Pair<String, String>) {
        loginState.value = UIScreenState(false, null, null)
        viewModelScope.launch {
            loginUserByFirebaseUseCase.login(credentials)
                .collect {
                    loginState.value = when (it) {
                        is MeviResult.Success -> UIScreenState(false, it.data, null)
                        is MeviResult.Error -> UIScreenState(false, null, it.error)
                    }
                }
        }
    }
}