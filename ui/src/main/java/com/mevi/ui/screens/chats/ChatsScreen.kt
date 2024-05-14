package com.mevi.ui.screens.chats

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.mevi.ui.navigation.NavigationGraphRoute

@Composable
fun ChatsScreen() {
    Text(text = NavigationGraphRoute.ROUTE_SCREEN_CHATS.route)
}