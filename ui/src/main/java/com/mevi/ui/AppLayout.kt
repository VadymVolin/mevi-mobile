package com.mevi.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.mevi.ui.components.BottomNavigationBar
import com.mevi.ui.navigation.NavigationComponent
import com.mevi.ui.navigation.host.ScreenNavigationHost
import com.mevi.ui.startup.standard.OnboardingChaneHandler

@Composable
fun AppLayout(
    navigationComponent: NavigationComponent,
    onboardingChaneHandler: OnboardingChaneHandler
) {
    val showAppBars = rememberSaveable {
        mutableStateOf(false)
    }
    val modifier = Modifier.fillMaxSize()
    // main screen container
    Scaffold(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        bottomBar = {
            if (showAppBars.value) {
                BottomNavigationBar(navigationComponent = navigationComponent)
            }
        }
    ) {
        ScreenNavigationHost(
            modifier = modifier
                .padding(it)
                .consumeWindowInsets(it)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal),
                ),
            navigationComponent = navigationComponent,
            onboardingChaneHandler = onboardingChaneHandler,
            updateAppBarsVisibility = { shouldShowAppBars: Boolean ->
                showAppBars.value = shouldShowAppBars
            }
        )
    }
}