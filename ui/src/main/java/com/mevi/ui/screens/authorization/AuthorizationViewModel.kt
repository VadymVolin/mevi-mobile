package com.mevi.ui.screens.authorization

import android.content.Context
import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.mevi.MainActivity.Companion.TAG
import com.mevi.domain.model.MeviError
import com.mevi.domain.model.MeviResult
import com.mevi.domain.repository.user.model.MeviUser
import com.mevi.domain.repository.user.usecase.LoginUserByFirebaseUseCase
import com.mevi.domain.repository.user.usecase.LoginUserByGoogleFirebaseUseCase
import com.mevi.domain.repository.user.usecase.RegisterUserByFirebaseUseCase
import com.mevi.domain.repository.user.usecase.model.RegisterUserModel
import com.mevi.ui.BuildConfig
import com.mevi.ui.screens.state.UIScreenState
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID

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
    private val loginUserByGoogleFirebaseUseCase: LoginUserByGoogleFirebaseUseCase,
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

    fun register(registerUserModel: RegisterUserModel) {
        val validationResult = validate(registerUserModel.email to registerUserModel.password)
        if (validationResult != null) {
            registrationState = UIScreenState(false, null, validationResult)
            return
        }
        registrationState = UIScreenState(true, null, null)
        viewModelScope.launch {
            registerUserByFirebaseUseCase.register(registerUserModel)
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

    fun loginByGoogle(context: Context) {
        loginState = UIScreenState(true, null, null)
        viewModelScope.launch {
            val googleIdToken = getGoogleCredentialTokenId(context)
            loginUserByGoogleFirebaseUseCase.login(googleIdToken)
                .collect {
                    loginState = when (it) {
                        is MeviResult.Success -> UIScreenState(false, it.data, null)
                        is MeviResult.Error -> UIScreenState(false, null, it.error)
                    }
                }
        }
    }

    private fun validate(credentials: Pair<String?, String?>) : MeviError? {
        val emailResult = !credentials.first.isValidEmailFormat()
        val passwordResult = !credentials.second.isValidPasswordFormat()
        return when {
            emailResult && passwordResult -> MeviError.ERROR_WRONG_EMAIL_OR_PASSWORD
            emailResult && !passwordResult -> MeviError.ERROR_INCORRECT_EMAIL
            !emailResult && passwordResult -> MeviError.ERROR_WEAK_PASSWORD
            else -> null
        }
    }

    private suspend fun getGoogleCredentialTokenId(context: Context): String? {
        try {
            // Initialize Credential Manager
            val credentialManager: CredentialManager = CredentialManager.create(context)

            // Generate a nonce (a random number used once)
            val ranNonce: String = UUID.randomUUID().toString()
            val md: MessageDigest = MessageDigest.getInstance("SHA-256")
            val digest: ByteArray = md.digest(ranNonce.toByteArray(Charsets.UTF_8))
            val hashedNonce: String = digest.fold("") { str, it -> str + "%02x".format(it) }

            // Set up Google ID option
            val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(BuildConfig.GOOGLE_WEB_CLIENT_ID)
                .setNonce(hashedNonce)
                .build()

            // Request credentials
            val request: GetCredentialRequest = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            // Get the credential result
            val result = credentialManager.getCredential(context, request)
            val credential = result.credential

            // Check if the received credential is a valid Google ID Token
            if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                return googleIdTokenCredential.idToken
            } else {
                throw Exception("Received an invalid credential type")
            }
        } catch (e: GetCredentialCancellationException) {
            Log.e(TAG, "getGoogleCredentialTokenId: ", e)
            return null
        }
    }
}

fun String?.isValidEmailFormat() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
fun String?.isValidPasswordFormat() = !isNullOrEmpty() && length >= 8