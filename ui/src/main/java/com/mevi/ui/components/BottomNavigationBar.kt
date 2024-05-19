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
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mevi.ui.R
import com.mevi.ui.navigation.NavigationComponent
import com.mevi.ui.navigation.Route

@Composable
fun BottomNavigationBar(
    bottomRoutes: Array<Route> = arrayOf(
        Route.Menu.ROUTE_SCREEN_CHATS,
        Route.Menu.ROUTE_SCREEN_RANDOM_CALL,
        Route.Menu.ROUTE_SCREEN_ACCOUNT
    ),
    navigationComponent: NavigationComponent
) =
    NavigationBar {
        val navBackStackEntry by navigationComponent.getScreenNavController().currentBackStackEntryAsState()
        bottomRoutes.forEach { item ->
            NavigationBarItem(
                icon = { Icon(getIconByRoute(item), contentDescription = item.route) },
                label = { Text(stringResource(id = getTextIdByRoute(item))) },
                selected = navBackStackEntry?.destination?.hierarchy?.any { it.route == item.route } == true,
                onClick = { navigationComponent.navigate(item) }
            )
        }
    }

fun getTextIdByRoute(route: Route) = when (route) {
    Route.Menu.ROUTE_SCREEN_CHATS -> R.string.TEXT_CHATS_ROUTE
    Route.Menu.ROUTE_SCREEN_RANDOM_CALL -> R.string.TEXT_RANDOM_CALL_ROUTE
    Route.Menu.ROUTE_SCREEN_ACCOUNT -> R.string.TEXT_ACCOUNT_ROUTE
    else -> R.string.N_A
}

fun getIconByRoute(route: Route) = when (route) {
    Route.Menu.ROUTE_SCREEN_CHATS -> Icons.AutoMirrored.Filled.Chat
    Route.Menu.ROUTE_SCREEN_RANDOM_CALL -> Icons.Filled.VideoCall
    Route.Menu.ROUTE_SCREEN_ACCOUNT -> Icons.Filled.ManageAccounts
    else -> Icons.Filled.Error
}