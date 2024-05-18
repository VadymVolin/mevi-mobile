package com.mevi.ui.screens.authorization

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.mevi.domain.model.MeviError
import com.mevi.domain.repository.user.model.MeviUser
import com.mevi.ui.R
import com.mevi.ui.components.tabs.Tabs
import com.mevi.ui.screens.state.UIScreenState
import com.mevi.ui.theme.MeviTheme
import okhttp3.internal.toImmutableList

enum class TabRoutes(val title: Int) {
    SIGN_IN(R.string.TEXT_SIGN_IN),
    SIGN_UP(R.string.TEXT_SIGN_UP),
}

@Composable
fun AuthorizationScreen(
    loginState: UIScreenState<MeviUser>,
    loginAction: (Pair<String, String>) -> Unit,
    registrationState: UIScreenState<MeviUser>,
    registrationAction: (Pair<String, String>) -> Unit,
    forgotPasswordAction: () -> Unit,
    onAuthenticated: () -> Unit
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val onTabClickAction = remember {
        { index: Int ->
            selectedTabIndex = index
        }
    }
    val tabTitlesId: List<Int> = TabRoutes.entries.map { it.title }.toImmutableList()
    // sign in fields
    val emailValue = rememberSaveable {
        mutableStateOf("")
    }
    val passwordValue = rememberSaveable {
        mutableStateOf("")
    }
    val onEmailValueChange = { value: String ->
        emailValue.value = value
    }
    val onPasswordValueChange = { value: String ->
        passwordValue.value = value
    }
    val isButtonEnabled =  remember(emailValue.value, passwordValue.value) {
        mutableStateOf(emailValue.value.isNotEmpty() && passwordValue.value.isNotEmpty())
    }
    Surface {
        Column(modifier = Modifier.fillMaxSize()) {
            Tabs(
                selectedTabIndex,
                tabTitlesId,
                onTabClick = onTabClickAction,
                modifier = Modifier
                    .fillMaxWidth(.5f)
                    .align(Alignment.CenterHorizontally)
            )
            when (selectedTabIndex) {
                0 -> SignInPane(emailValue, onEmailValueChange, passwordValue, onPasswordValueChange, loginState, loginAction, forgotPasswordAction, isButtonEnabled.value, onAuthenticated)
                1 -> SignUpPane(registrationState, registrationAction)
            }
        }
    }
}

enum class FieldType {
    EMAIL, PASSWORD
}

fun getErrorText(fieldType: FieldType, error: MeviError) = when {
    fieldType == FieldType.EMAIL && error == MeviError.ERROR_WEAK_PASSWORD -> 0
    fieldType == FieldType.PASSWORD && error == MeviError.ERROR_WEAK_PASSWORD -> R.string.ERROR_WEAK_PASSWORD
    fieldType == FieldType.EMAIL && error == MeviError.ERROR_INCORRECT_EMAIL -> R.string.ERROR_INCORRECT_EMAIL
    fieldType == FieldType.PASSWORD && error == MeviError.ERROR_INCORRECT_EMAIL -> 0
    fieldType == FieldType.EMAIL && error == MeviError.ERROR_ACCOUNT_DOES_NO_EXIST -> R.string.ERROR_ACCOUNT_DOES_NO_EXIST
    fieldType == FieldType.PASSWORD && error == MeviError.ERROR_ACCOUNT_DOES_NO_EXIST -> 0
    fieldType == FieldType.EMAIL && error == MeviError.ERROR_ACCOUNT_ALREADY_EXISTS -> R.string.ERROR_ACCOUNT_ALREADY_EXISTS
    fieldType == FieldType.PASSWORD && error == MeviError.ERROR_ACCOUNT_ALREADY_EXISTS -> 0
    fieldType == FieldType.EMAIL && error == MeviError.ERROR_WRONG_EMAIL_OR_PASSWORD -> R.string.ERROR_WRONG_EMAIL
    fieldType == FieldType.PASSWORD && error == MeviError.ERROR_WRONG_EMAIL_OR_PASSWORD -> R.string.ERROR_WRONG_PASSWORD
    fieldType == FieldType.EMAIL && error == MeviError.ERROR_UNEXPECTED -> R.string.ERROR_UNEXPECTED
    fieldType == FieldType.PASSWORD && error == MeviError.ERROR_UNEXPECTED -> R.string.ERROR_UNEXPECTED
    else -> R.string.ERROR_UNEXPECTED
}


@Preview(
    showBackground = true, device = Devices.PIXEL_3, showSystemUi = true
)
@Preview(
    showBackground = true,
    device = Devices.PIXEL_3,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun AuthorizationPreview() {
    MeviTheme {
        AuthorizationScreen(
            UIScreenState(false, null, null),
            {},
            UIScreenState(false, null, null),
            {},
            {},
            {}
        )
    }
}