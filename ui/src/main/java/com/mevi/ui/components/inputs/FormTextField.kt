package com.mevi.ui.components.inputs

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Umbrella
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mevi.ui.components.buttons.MeviIconButton
import com.mevi.ui.theme.MeviTheme

@Composable
fun FormTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit?,
    label: String? = null,
    placeholder: String? = null,
    leadingIcon: ImageVector? = null,
    isPassword: Boolean = false,
    isError: Boolean = false,
    errorText: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    var passwordVisibility: Boolean by remember { mutableStateOf(false) }
    fun getVisualTransformation(): VisualTransformation {
        return if (isPassword) {
            if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        }
    }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth() then modifier,
        value = value,
        onValueChange = { onValueChange(it) },
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        label = if (label != null) {
            {
                Text(
                    text = label, style = MaterialTheme.typography.bodySmall
                )
            }
        } else {
            null
        },
        placeholder = if (placeholder != null) {
            {
                Text(
                    text = placeholder, style = MaterialTheme.typography.titleMedium
                )
            }
        } else {
            null
        },
        isError = isError,
        supportingText = if (errorText != null) {
            {
                Text(
                    text = errorText, style = MaterialTheme.typography.titleSmall
                )
            }
        } else {
            null
        },
        shape = RoundedCornerShape(16.dp),
        visualTransformation = getVisualTransformation(),
        leadingIcon = {
            leadingIcon?.let {
                Icon(
                    contentDescription = null, modifier = Modifier, imageVector = it
                )
            }
            if (isPassword) {
                Icon(
                    contentDescription = null,
                    modifier = Modifier,
                    imageVector = Icons.Outlined.Lock
                )
            }
        },
        trailingIcon = if (isPassword && value.isNotEmpty()) {
            {
                MeviIconButton(
                    onClick = { passwordVisibility = !passwordVisibility },
                    icon = if (passwordVisibility) Icons.Outlined.VisibilityOff
                    else Icons.Outlined.Visibility,
                )
            }
        } else {
            null
        },
    )
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
fun FormTextFieldPreview() {
    MeviTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.padding(top = 50.dp, start = 16.dp, end = 16.dp)) {
                Column {
                    FormTextField(
                        placeholder = "Login",
                        value = "Login",
                        onValueChange = {},
                        leadingIcon = Icons.Outlined.Umbrella
                    )
                    FormTextField(
                        placeholder = "Error",
                        value = "Error",
                        onValueChange = {},
                        leadingIcon = Icons.Outlined.ErrorOutline,
                        isError = true,
                        errorText = "Error text"
                    )
                    FormTextField(placeholder = "Password",
                        isPassword = true,
                        value = "Password",
                        onValueChange = {})
                }
            }
        }
    }
}