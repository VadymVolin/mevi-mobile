package com.mevi.ui.chats

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.mevi.ui.navigation.NavigationRoute

@Composable
fun ChatsScreen() {
    Text(text = NavigationRoute.ROUTE_CHATS.route)
}