package com.mevi.ui.screens.authorization

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mevi.domain.model.MeviError
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
    var registrationState by mutableStateOf(UIScreenState<MeviUser>(false, null, null))
        private set

    /**
     * Login state
     */
    var loginState by mutableStateOf(UIScreenState<MeviUser>(false, null, null))
        private set

    fun register(credentials: Pair<String, String>) {
        val validationResult = validate(credentials)
        if (validationResult != null) {
            registrationState = UIScreenState(false, null, validationResult)
            return
        }
        registrationState = UIScreenState(true, null, null)
        viewModelScope.launch {
            registerUserByFirebaseUseCase.register(credentials)
                .collect {
                    registrationState = when (it) {
                        is MeviResult.Success -> UIScreenState(false, it.data, null)
                        is MeviResult.Error -> UIScreenState(false, null, it.error)
                    }
                }
        }
    }

    fun login(credentials: Pair<String, String>) {
        val validationResult = validate(credentials)
        if (validationResult != null) {
            loginState = UIScreenState(false, null, validationResult)
            return
        }
        loginState = UIScreenState(true, null, null)
        viewModelScope.launch {
            loginUserByFirebaseUseCase.login(credentials)
                .collect {
                    loginState = when (it) {
                        is MeviResult.Success -> UIScreenState(false, it.data, null)
                        is MeviResult.Error -> UIScreenState(false, null, it.error)
                    }
                }
        }
    }

    private fun validate(credentials: Pair<String, String>) : MeviError? {
        val emailResult = !credentials.first.isValidEmailFormat()
        val passwordResult = !credentials.second.isValidPasswordFormat()
        return when {
            emailResult && passwordResult -> MeviError.ERROR_WRONG_EMAIL_OR_PASSWORD
            emailResult && !passwordResult -> MeviError.ERROR_INCORRECT_EMAIL
            !emailResult && passwordResult -> MeviError.ERROR_WEAK_PASSWORD
            else -> null
        }
    }
}

fun String?.isValidEmailFormat() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
fun String?.isValidPasswordFormat() = !isNullOrEmpty() && length >= 8