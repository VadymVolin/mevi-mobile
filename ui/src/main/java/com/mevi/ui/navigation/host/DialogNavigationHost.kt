package com.mevi.ui.navigation.host

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mevi.ui.account.AccountScreen
import com.mevi.ui.call.RandomCallScreen
import com.mevi.ui.chats.ChatsScreen
import com.mevi.ui.navigation.NavigationRoute

@Composable
fun DialogNavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) = NavHost(
    navController = navController,
    startDestination = NavigationRoute.ROUTE_CHATS.route,
    modifier = modifier
) {
    composable(NavigationRoute.ROUTE_CHATS.route) {
        ChatsScreen()
    }
    composable(NavigationRoute.ROUTE_RANDOM_CALL.route) {
        RandomCallScreen()
    }
    composable(NavigationRoute.ROUTE_ACCOUNT.route) {
        AccountScreen()
    }
}