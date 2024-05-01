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
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mevi.ui.components.BottomNavigationBar
import com.mevi.ui.navigation.NavigationComponent
import com.mevi.ui.navigation.host.AlertNavigationHost
import com.mevi.ui.navigation.host.DialogNavigationHost
import com.mevi.ui.navigation.host.NotificationNavigationHost
import com.mevi.ui.navigation.host.ScreenNavigationHost

@Composable
fun MainContainerLayout(
    navigationComponent: NavigationComponent
) {
    val modifier = Modifier.fillMaxSize()
    Surface (modifier = modifier) {
        // main screen container
        Scaffold(
            modifier = modifier.background(MaterialTheme.colorScheme.background),
            bottomBar = {
                BottomNavigationBar(navigationComponent = navigationComponent)
            }
        ) {
            ScreenNavigationHost(
                modifier = modifier
                    .padding(it)
                    .consumeWindowInsets(it)
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal),
                    ),
                navigationComponent = navigationComponent
            )
        }
        DialogNavigationHost(modifier.background(Color(android.graphics.Color.TRANSPARENT)), navigationComponent)
        AlertNavigationHost(modifier.background(Color(android.graphics.Color.TRANSPARENT)), navigationComponent)
        NotificationNavigationHost(modifier.background(Color(android.graphics.Color.TRANSPARENT)), navigationComponent)
    }

}