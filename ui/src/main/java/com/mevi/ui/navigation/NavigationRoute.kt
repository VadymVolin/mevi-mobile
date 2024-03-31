package com.mevi.ui.navigation

import com.mevi.common.translations.TextKey

/**
 * Navigation route,
 * @see BottomNavigationBarHost
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 3/2/24
 */
enum class NavigationRoute(
    val route: String,
    val nameKey: TextKey
) {
    // main menu routes
    ROUTE_CHATS("CHATS", TextKey.TEXT_CHATS_ROUTE),
    ROUTE_RANDOM_CALL("RANDOM_CALL", TextKey.TEXT_RANDOM_CALL_ROUTE),
    ROUTE_ACCOUNT("ACCOUNT", TextKey.TEXT_ACCOUNT_ROUTE)

    // dialog routes

    // alerts routes

    // notifications routes
}