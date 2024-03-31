package com.mevi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mevi.ui.account.AccountScreen
import com.mevi.ui.call.RandomCallScreen
import com.mevi.ui.chats.ChatsScreen

@Composable
fun BottomNavigationBarHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
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