package com.mevi.ui.screens.authorization


import android.annotation.SuppressLint
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.mevi.common.utils.Country
import com.mevi.common.utils.getCountries
import com.mevi.domain.repository.user.model.MeviUser
import com.mevi.ui.R
import com.mevi.ui.components.WelcomeSection
import com.mevi.ui.components.buttons.MeviButton
import com.mevi.ui.components.buttons.MeviIconButton
import com.mevi.ui.components.dialogs.CountrySelector
import com.mevi.ui.components.dialogs.MeviDialog
import com.mevi.ui.components.inputs.FormTextField
import com.mevi.ui.screens.state.UIScreenState
import java.util.ArrayList
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SignUpPane(
    registrationState: UIScreenState<MeviUser>,
    registrationAction: (Pair<String, String>) -> Unit
) {
    val nameValue = rememberSaveable {
        mutableStateOf("")
    }
    val emailValue = rememberSaveable {
        mutableStateOf("")
    }
    val passwordValue = rememberSaveable {
        mutableStateOf("")
    }
    val repeatPasswordValue = rememberSaveable {
        mutableStateOf("")
    }
    val selectedCountry: MutableState<Country?> = remember {
        mutableStateOf(null)
    }

    fun onSelectCountry(value: Country) {
        selectedCountry.value = value
    }

    fun onNameValueChange(value: String) {
        nameValue.value = value
    }

    fun onEmailValueChange(value: String) {
        emailValue.value = value
    }

    fun onPasswordValueChange(value: String) {
        passwordValue.value = value
    }

    fun onRepeatPasswordValueChange(value: String) {
        repeatPasswordValue.value = value
    }

    val buttonEnabled by rememberSaveable(emailValue, passwordValue) {
        mutableStateOf(emailValue.value.isNotEmpty() && passwordValue.value.isNotEmpty())
    }
    var popupVisibility by rememberSaveable { mutableStateOf(false) }
    fun showPopup() {
        popupVisibility = true

    }

    fun hidePopup() {
        popupVisibility = false

    }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    if (popupVisibility) {
        MeviDialog(onDismissRequest = { hidePopup() }) {
            CountrySelector(
                selectedCountry = selectedCountry.value,
                onSelectCountry = { onSelectCountry(it) },
                onDismissRequest = { hidePopup() })
        }
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .imePadding()
            .verticalScroll(rememberScrollState())
    ) {
        WelcomeSection(secondaryText = stringResource(id = R.string.TEXT_WELCOME_SECTION_CREATE_ACCOUNT))
        Spacer(modifier = Modifier.height(40.dp))

        FormTextField(
            modifier = Modifier.focusRequester(focusRequester),
            placeholder = stringResource(id = R.string.TEXTFIELD_PLACEHOLDER_NAME),
            value = nameValue.value,
            onValueChange = { onNameValueChange(it) },
            leadingIcon = Icons.Outlined.Person,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
        )
        Spacer(modifier = Modifier.height(8.dp))
        FormTextField(
            modifier = Modifier.focusRequester(focusRequester),
            placeholder = stringResource(id = R.string.TEXTFIELD_PLACEHOLDER_EMAIL),
            value = emailValue.value,
            onValueChange = { onEmailValueChange(it) },
            leadingIcon = Icons.Outlined.Mail,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
        )
        Spacer(modifier = Modifier.height(8.dp))

        FormTextField(
            placeholder = stringResource(id = R.string.TEXTFIELD_PLACEHOLDER_PASSWORD),
            isPassword = true,
            value = passwordValue.value,
            onValueChange = { onPasswordValueChange(it) },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done

            ),

            )
        Spacer(modifier = Modifier.height(8.dp))
        FormTextField(
            modifier = Modifier.focusRequester(focusRequester),
            placeholder = stringResource(id = R.string.TEXTFIELD_PLACEHOLDER_REPEAT_PASSWORD),
            isPassword = true,
            value = repeatPasswordValue.value,
            onValueChange = { onRepeatPasswordValueChange(it) },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done

            ),
            keyboardActions = KeyboardActions(onDone = {
                // Handle button click action here
                Log.d("SUBMIT", "Btn click")
                keyboardController?.hide()
                focusManager.clearFocus()
            })
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(onClick = { /*TODO*/ }) {
            Text(text = "text")
        }
        TextButton(modifier = Modifier.align(Alignment.End), onClick = { /*TODO*/ }) {
            Text(
                text = stringResource(id = R.string.BUTTON_FORGOT_PASSWORD),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        MeviButton(
            onClick = { Log.d("SUBMIT", "Btn click") },
            text = stringResource(id = R.string.TEXT_SIGN_IN),
            enabled = buttonEnabled,
        )
        MeviButton(
            onClick = { showPopup() },
            text = "showPopup",

            )
    }
}


//@Preview(showBackground = true, device = Devices.PIXEL_6_PRO)
//@Preview(
//    showBackground = true,
//    uiMode = Configuration.UI_MODE_NIGHT_YES,
//    device = Devices.PIXEL_6_PRO
//)
//@Composable
//fun SimpleComposablePreview() {
//    MeviTheme {
//
//        SignIn()
//    }
//}