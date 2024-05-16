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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.mevi.domain.repository.user.model.MeviUser
import com.mevi.ui.R
import com.mevi.ui.components.WelcomeSection
import com.mevi.ui.components.buttons.MeviButton
import com.mevi.ui.components.buttons.MeviIconButton
import com.mevi.ui.components.inputs.FormTextField
import com.mevi.ui.screens.state.UIScreenState

@Composable
fun SignInPane(
    emailValue: MutableState<String>,
    onEmailValueChange: (String) -> Unit,
    passwordValue: MutableState<String>,
    onPasswordValueChange: (String) -> Unit,
    loginState: UIScreenState<MeviUser>,
    loginAction: (Pair<String, String>) -> Unit,
    forgotPasswordAction: () -> Unit,
    isButtonEnabled: Boolean
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val emailErrorStringId = remember(loginState.error) {
        mutableIntStateOf(loginState.error?.let { getErrorText(FieldType.EMAIL, it) } ?: 0)
    }
    val passwordErrorStringId = remember(loginState.error) {
        mutableIntStateOf(loginState.error?.let { getErrorText(FieldType.PASSWORD, it) } ?: 0)
    }
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
                placeholder = stringResource(id = R.string.FORMTEXTFIELD_EMAIL_PLACEHOLDER),
                value = emailValue.value,
                onValueChange = onEmailValueChange,
                leadingIcon = Icons.Outlined.Mail,
                isError = emailErrorStringId.intValue > 0,
                errorText = if (emailErrorStringId.intValue > 0) stringResource(id = emailErrorStringId.intValue) else null,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            FormTextField(
                placeholder = stringResource(id = R.string.FORMTEXTFIELD_PASSWORD_PLACEHOLDER),
                isPassword = true,
                value = passwordValue.value,
                onValueChange = onPasswordValueChange,
                isError = passwordErrorStringId.intValue > 0,
                errorText = if (passwordErrorStringId.intValue > 0) stringResource(id = passwordErrorStringId.intValue) else null,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    // Handle button click action here
                    if (isButtonEnabled) {
                        loginAction(emailValue.value to passwordValue.value)
                    }
                    keyboardController?.hide()
                })
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(modifier = Modifier.align(Alignment.End), onClick = {
                forgotPasswordAction()
            }) {
                Text(
                    text = stringResource(id = R.string.TEXT_FORGOT_PASSWORD),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium,
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            MeviButton(
                onClick = {
                    loginAction(emailValue.value to passwordValue.value)
                },
                text = stringResource(id = R.string.TEXT_SIGN_IN),
                enabled = isButtonEnabled,
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