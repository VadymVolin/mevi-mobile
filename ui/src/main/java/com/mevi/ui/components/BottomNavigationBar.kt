package com.mevi.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mevi.common.translations.TextMatcher
import com.mevi.ui.navigation.NavigationRoute

@Composable
fun BottomNavigationBar(
    bottomRoutes: Array<NavigationRoute> = NavigationRoute.entries.toTypedArray(),
    navigationController: NavHostController,
    textMatcher: TextMatcher) =
    NavigationBar {
        val navBackStackEntry by navigationController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        bottomRoutes.forEach { item ->
            NavigationBarItem(
                icon = { Icon(getIconByRoute(item), contentDescription = item.route) },
                label = { Text(textMatcher.get(item.nameKey)) },
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {  }
            )
        }
    }

fun getIconByRoute(route: NavigationRoute) = when (route) {
    NavigationRoute.ROUTE_CHATS -> Icons.AutoMirrored.Filled.Chat
    NavigationRoute.ROUTE_RANDOM_CALL -> Icons.Filled.VideoCall
    NavigationRoute.ROUTE_ACCOUNT -> Icons.Filled.ManageAccounts
}