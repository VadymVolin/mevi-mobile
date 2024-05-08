package com.mevi.ui.screens.authorization

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.mevi.domain.model.MeviError
import com.mevi.domain.repository.user.model.MeviUser
import com.mevi.ui.R
import com.mevi.ui.components.WelcomeSection
import com.mevi.ui.components.buttons.MeviButton
import com.mevi.ui.components.buttons.MeviIconButton
import com.mevi.ui.components.inputs.FormTextField
import com.mevi.ui.screens.state.UIScreenState

@Composable
fun SignInPane(loginState: UIScreenState<MeviUser>, loginAction: (String, String) -> Unit) {
    var emailValue by rememberSaveable {
        mutableStateOf("")
    }
    var passwordValue by rememberSaveable {
        mutableStateOf("")
    }
    fun onEmailValueChange(value: String) {
        emailValue = value
    }
    fun onPasswordValueChange(value: String) {
        passwordValue = value
    }
    val buttonEnabled by rememberSaveable(emailValue, passwordValue) {
        mutableStateOf(emailValue.isNotEmpty() && passwordValue.isNotEmpty())
    }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        WelcomeSection(secondaryText = stringResource(id = R.string.WELCOME_SECTION_TEXT))
        Spacer(modifier = Modifier.height(40.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            FormTextField(
                modifier = Modifier.focusRequester(focusRequester),
                placeholder = stringResource(id = R.string.FORMTEXTFIELD_EMAIL_PLACEHOLDER),
                value = emailValue,
                onValueChange = { onEmailValueChange(it) },
                leadingIcon = Icons.Outlined.Mail,
                isError = loginState.error != null,
                errorText = loginState.error?.let { stringResource(id = getErrorText(it)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            FormTextField(
                modifier = Modifier.focusRequester(focusRequester),
                placeholder = stringResource(id = R.string.FORMTEXTFIELD_PASSWORD_PLACEHOLDER),
                isPassword = true,
                value = passwordValue,
                onValueChange = { onPasswordValueChange(it) },
                isError = loginState.error != null,
                errorText = loginState.error?.let { stringResource(id = getErrorText(it)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done

                ),
                keyboardActions = KeyboardActions(onDone = {
                    // Handle button click action here
                    loginAction(emailValue, passwordValue)
                    keyboardController?.hide()
                    focusManager.clearFocus()
                })
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(modifier = Modifier.align(Alignment.End), onClick = { /*TODO*/ }) {
                Text(
                    text = stringResource(id = R.string.TEXT_FORGOT_PASSWORD),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            MeviButton(
                onClick = { loginAction(emailValue, passwordValue) },
                text = stringResource(id = R.string.TEXT_SIGN_IN),
                enabled = buttonEnabled,
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.TEXT_OR_SIGN_IN_WITH),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                MeviIconButton(
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(8.dp)
                    ),
                    onClick = { /*TODO*/ },
                    icon = R.drawable.ic_google,
                    iconColor = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(8.dp))
                MeviIconButton(
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(8.dp)
                    ),
                    onClick = { /*TODO*/ },
                    icon = R.drawable.ic_facebook,
                    iconColor = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(8.dp))
                MeviIconButton(
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(8.dp)
                    ),
                    onClick = { /*TODO*/ },
                    icon = R.drawable.ic_apple,
                    iconColor = Color.Unspecified
                )
            }
        }
    }
}

fun getErrorText(error: MeviError) = when(error) {
    MeviError.ERROR_WEAK_PASSWORD -> R.string.ERROR_WEAK_PASSWORD
    MeviError.ERROR_INCORRECT_EMAIL -> R.string.ERROR_INCORRECT_EMAIL
    MeviError.ERROR_ACCOUNT_DOES_NO_EXIST -> R.string.ERROR_ACCOUNT_DOES_NO_EXIST
    MeviError.ERROR_ACCOUNT_ALREADY_EXISTS -> R.string.ERROR_ACCOUNT_ALREADY_EXISTS
    MeviError.ERROR_WRONG_EMAIL_OR_PASSWORD -> R.string.ERROR_WRONG_EMAIL_OR_PASSWORD
    MeviError.ERROR_UNEXPECTED -> R.string.ERROR_UNEXPECTED
}