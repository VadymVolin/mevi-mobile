package com.mevi.ui.screens.authorization


import android.view.Gravity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Transgender
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.mevi.domain.repository.user.model.MeviUser
import com.mevi.domain.repository.user.usecase.model.Country
import com.mevi.domain.repository.user.usecase.model.Gender
import com.mevi.domain.repository.user.usecase.model.RegisterUserModel
import com.mevi.ui.R
import com.mevi.ui.components.WelcomeSection
import com.mevi.ui.components.buttons.MeviButton
import com.mevi.ui.components.buttons.MeviSelectorButton
import com.mevi.ui.components.dialogs.CountrySelector
import com.mevi.ui.components.dialogs.GenderSelector
import com.mevi.ui.components.dialogs.MeviDialog
import com.mevi.ui.components.inputs.FormTextField
import com.mevi.ui.model.CountryModel
import com.mevi.ui.model.GenderModel
import com.mevi.ui.screens.state.UIScreenState

@Composable
fun SignUpPane(
    registrationState: UIScreenState<MeviUser>,
    registrationAction: (RegisterUserModel) -> Unit,
    onAuthenticated: () -> Unit
) {
    if (registrationState.data != null) {
        onAuthenticated()
    }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

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
    val selectedCountry: MutableState<CountryModel?> = remember {
        mutableStateOf(null)
    }
    val selectedGender: MutableState<GenderModel?> = remember {
        mutableStateOf(null)
    }
    val buttonEnabled by rememberSaveable(
        emailValue.value,
        passwordValue.value,
        repeatPasswordValue.value,
        selectedCountry.value,
        selectedGender.value
    ) {
        mutableStateOf(emailValue.value.isNotEmpty() && passwordValue.value.isNotEmpty() && repeatPasswordValue.value.isNotEmpty() && selectedCountry.value != null && selectedGender.value != null)
    }
    var countryPopupVisibility by rememberSaveable { mutableStateOf(false) }
    var genderPopupVisibility by rememberSaveable { mutableStateOf(false) }

    fun showCountryPopup() {
        focusManager.clearFocus()
        countryPopupVisibility = true
    }

    fun hideCountryPopup() {
        countryPopupVisibility = false
    }

    fun showGenderPopup() {
        focusManager.clearFocus()
        genderPopupVisibility = true
    }

    fun hideGenderPopup() {
        genderPopupVisibility = false
    }

    fun onSelectCountry(value: CountryModel) {
        selectedCountry.value = value
        hideCountryPopup()
    }

    fun onSelectGender(value: GenderModel) {
        selectedGender.value = value
        hideGenderPopup()
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

    if (countryPopupVisibility) {
        MeviDialog(dialogGravity = Gravity.TOP, onDismissRequest = { hideCountryPopup() }) {
            CountrySelector(selectedCountry = selectedCountry.value,
                onSelectCountry = { onSelectCountry(it) },
                onDismissRequest = { hideCountryPopup() })
        }
    }
    if (genderPopupVisibility) {
        MeviDialog(onDismissRequest = { hideGenderPopup() }) {
            GenderSelector(
                selectedGender = selectedGender.value,
                onSelectGender = { onSelectGender(it) },
            )
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
                imeAction = ImeAction.Next

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
                keyboardController?.hide()
                focusManager.clearFocus()
            })
        )
        Spacer(modifier = Modifier.height(8.dp))
        MeviSelectorButton(text = selectedCountry.value?.countryName
            ?: stringResource(R.string.BUTTON_SELECT_COUNTRY),
            icon = Icons.Default.Language,
            onClick = { showCountryPopup() })
        Spacer(modifier = Modifier.height(8.dp))
        MeviSelectorButton(text = selectedGender.value?.type
            ?: stringResource(R.string.BUTTON_SELECT_GENDER),
            icon = Icons.Default.Transgender,
            onClick = { showGenderPopup() })
        TextButton(modifier = Modifier.align(Alignment.End), onClick = { /*TODO*/ }) {
            Text(
                text = stringResource(id = R.string.BUTTON_FORGOT_PASSWORD),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        MeviButton(
            onClick = {
                registrationAction(
                    RegisterUserModel(
                        email = emailValue.value,
                        password = passwordValue.value,
                        name = nameValue.value,
                        country = selectedCountry.value?.let { Country(it.isoCode, it.countryName, it.flag) },
                        gender = selectedGender.value?.let { Gender(it.type) }
                    )
                )
            },
            text = stringResource(id = R.string.TEXT_SIGN_UP),
            enabled = buttonEnabled,
        )

    }
}