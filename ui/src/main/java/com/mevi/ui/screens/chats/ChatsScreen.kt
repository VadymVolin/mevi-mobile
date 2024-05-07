package com.mevi.ui.screens.chats

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.mevi.ui.navigation.NavigationRoute

@Composable
fun ChatsScreen() {
    Text(text = NavigationRoute.ROUTE_SCREEN_CHATS.route)
}