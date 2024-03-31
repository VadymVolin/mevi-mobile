package com.mevi.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mevi.common.translations.TextMatcher
import com.mevi.ui.components.BottomNavigationBar

@Composable
fun MainContainerLayout(navHostController: NavHostController = rememberNavController(), textMatcher: TextMatcher) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        bottomBar = {
            BottomNavigationBar(navigationController = rememberNavController(), textMatcher = textMatcher)
        }
    ) {

    }
}