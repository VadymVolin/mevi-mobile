package com.mevi.ui.screens.authorization

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mevi.ui.R
import com.mevi.ui.components.inputs.FormTextField
import com.mevi.ui.components.WelcomeSection
import com.mevi.ui.components.buttons.MeviButton
import com.mevi.ui.components.buttons.MeviIconButton
import com.mevi.ui.theme.MeviTheme

@Composable
fun SignInScreen() {
    var emailValue by rememberSaveable {
        mutableStateOf("")
    }
    var passwordValue by rememberSaveable {
        mutableStateOf("")
    }
    var buttonEnabled by rememberSaveable(emailValue, passwordValue) {
        mutableStateOf(emailValue.isNotEmpty() && passwordValue.isNotEmpty())
    }

    fun onEmailValueChange(value: String) {
        emailValue = value
    }

    fun onPasswordValueChange(value: String) {
        passwordValue = value
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
        WelcomeSection(secondaryText = "Welcome back! Please enter your details")
        Spacer(modifier = Modifier.height(40.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            FormTextField(modifier = Modifier.focusRequester(focusRequester),
                placeholder = "Enter email",
                value = emailValue,
                onValueChange = { onEmailValueChange(it) },
                leadingIcon = Icons.Outlined.Mail,
                keyboardOptions = KeyboardOptions.Default.copy(
                    // todo error check on ImeAction.Done
                    imeAction = if (buttonEnabled) ImeAction.Done else ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onDone = {
                    // Handle submit button click action here
                    Log.d("SUBMIT", "Btn click")
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }))
            Spacer(modifier = Modifier.height(8.dp))
            FormTextField(modifier = Modifier.focusRequester(focusRequester),
                placeholder = "Enter password",
                isPassword = true,
                value = passwordValue,
                onValueChange = { onPasswordValueChange(it) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    // todo error check on ImeAction.Done
                    imeAction = if (buttonEnabled) ImeAction.Done else ImeAction.Next

                ),
                keyboardActions = KeyboardActions(onDone = {
                    // Handle button click action here
                    Log.d("SUBMIT", "Btn click")
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }))
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(modifier = Modifier.align(Alignment.End), onClick = { /*TODO*/ }) {
                Text(
                    text = "Forgot password?",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            MeviButton(
                onClick = { Log.d("SUBMIT", "Btn click") },
                text = "Sign in",
                enabled = buttonEnabled,
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Or sign in with",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                MeviIconButton(onClick = { /*TODO*/ }, icon = R.drawable.ic_google)
                Spacer(modifier = Modifier.width(8.dp))
                MeviIconButton(onClick = { /*TODO*/ }, icon = R.drawable.ic_facebook)
                Spacer(modifier = Modifier.width(8.dp))
                MeviIconButton(onClick = { /*TODO*/ }, icon = R.drawable.ic_apple)
            }
        }
    }
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
fun SimpleComposablePreview() {
    MeviTheme() {
        Surface(modifier = Modifier.fillMaxSize()) {
            SignInScreen()
        }
    }
}