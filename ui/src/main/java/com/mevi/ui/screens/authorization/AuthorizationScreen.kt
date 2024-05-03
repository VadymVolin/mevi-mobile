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
import com.mevi.ui.components.tabs.Tabs
import com.mevi.ui.theme.MeviTheme

enum class TabRoutes(val title: String) {
    SIGN_IN("Sign in"),
    SIGN_UP("Sign up"),
}

@Composable
fun AuthorizationScreen() {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    fun onTabClick(index: Int) {
        selectedTabIndex = index
    }

    val tabTitles = TabRoutes.entries.map { it.title }
    Surface {
        Column(modifier = Modifier.fillMaxWidth()) {
            Tabs(
                selectedTabIndex,
                tabTitles,
                onTabClick = { onTabClick(it) },
                modifier = Modifier
                    .fillMaxWidth(.5f)
                    .align(Alignment.CenterHorizontally)
            )
            when (selectedTabIndex) {
                0 -> SignInScreen()
                1 -> SignUpScreen()
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