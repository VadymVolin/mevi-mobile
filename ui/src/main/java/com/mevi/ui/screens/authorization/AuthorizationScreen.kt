package com.mevi.ui.screens.authorization

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.mevi.ui.R
import com.mevi.ui.components.tabs.Tabs
import com.mevi.ui.theme.MeviTheme
import org.koin.androidx.compose.koinViewModel

enum class TabRoutes(val title: Int) {
    SIGN_IN(R.string.TEXT_SIGN_IN),
    SIGN_UP(R.string.TEXT_SIGN_UP),
}

@Composable
fun AuthorizationScreen(viewModel: AuthorizationViewModel = koinViewModel()) {
    val loginState = remember { viewModel.loginState }
    val registrationState = remember { viewModel.registrationState }
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    fun onTabClick(index: Int) {
        selectedTabIndex = index
    }

    val tabTitlesId = TabRoutes.entries.map { it.title }
    Surface {
        Column(modifier = Modifier.fillMaxWidth()) {
            Tabs(
                selectedTabIndex,
                tabTitlesId,
                onTabClick = { onTabClick(it) },
                modifier = Modifier
                    .fillMaxWidth(.5f)
                    .align(Alignment.CenterHorizontally)
            )
            when (selectedTabIndex) {
                0 -> SignInPane(loginState.value) { email: String, password: String ->
                    viewModel.login(email to password)
                }

                1 -> SignUpPane(registrationState) { email: String, password: String ->
                    viewModel.register(email to password)
                }
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
fun AuthorizationPreview() {
    MeviTheme {
        AuthorizationScreen()
    }
}