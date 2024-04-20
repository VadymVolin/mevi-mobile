package com.mevi.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mevi.ui.translations.TextKey
import com.mevi.ui.translations.TextMatcher
import com.mevi.ui.navigation.NavigationComponent
import com.mevi.ui.navigation.NavigationRoute

@Composable
fun BottomNavigationBar(
    bottomRoutes: Array<NavigationRoute> = arrayOf(
        NavigationRoute.ROUTE_SCREEN_CHATS, NavigationRoute.ROUTE_SCREEN_RANDOM_CALL, NavigationRoute.ROUTE_SCREEN_ACCOUNT
    ),
    navigationComponent: NavigationComponent,
    textMatcher: TextMatcher
) =
    NavigationBar {
        val navBackStackEntry by navigationComponent.getScreenNavController().currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        bottomRoutes.forEach { item ->
            NavigationBarItem(
                icon = { Icon(getIconByRoute(item), contentDescription = item.route) },
                label = { Text(textMatcher[item.nameKey ?: TextKey.NO_INFO]) },
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = { navigationComponent.showScreen(item) }
            )
        }
    }


fun getIconByRoute(route: NavigationRoute) = when (route) {
    NavigationRoute.ROUTE_SCREEN_CHATS -> Icons.AutoMirrored.Filled.Chat
    NavigationRoute.ROUTE_SCREEN_RANDOM_CALL -> Icons.Filled.VideoCall
    NavigationRoute.ROUTE_SCREEN_ACCOUNT -> Icons.Filled.ManageAccounts
    else -> Icons.Filled.Error
}