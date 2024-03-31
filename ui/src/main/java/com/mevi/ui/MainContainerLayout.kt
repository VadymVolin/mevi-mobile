package com.mevi.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mevi.common.translations.TextMatcher
import com.mevi.ui.components.BottomNavigationBar
import com.mevi.ui.navigation.BottomNavigationBarHost

@Composable
fun MainContainerLayout(
    navHostController: NavHostController = rememberNavController(),
    textMatcher: TextMatcher
) {
    val modifier = Modifier.fillMaxSize()
    Scaffold(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        bottomBar = {
            BottomNavigationBar(navigationController = navHostController, textMatcher = textMatcher)
        }
    ) {
        Box(
            modifier = modifier
                .padding(it)
                .consumeWindowInsets(it)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal),
                )
        ) {
            BottomNavigationBarHost(modifier = modifier, navController = navHostController)
        }
    }
}