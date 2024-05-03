package com.mevi.ui.navigation

/**
 * Navigation route,
 * @see com.mevi.ui.navigation.host.ScreenNavigationHost
 * @see com.mevi.ui.navigation.host.DialogNavigationHost
 * @see com.mevi.ui.navigation.host.AlertNavigationHost
 * @see com.mevi.ui.navigation.host.NotificationNavigationHost
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 3/2/24
 */
enum class NavigationRoute(
    val route: String,
    val removeLastRoute: Boolean = false
) {
    ROUTE_EMPTY("EMPTY"),
    // main screen routes
    ROUTE_SCREEN_CHATS("CHATS"),
    ROUTE_SCREEN_RANDOM_CALL("RANDOM_CALL"),
    ROUTE_SCREEN_ACCOUNT("ACCOUNT"),

    // dialog routes
    ROUTE_DIALOG_AUTHORIZATION_DIALOG("AUTHORIZATION_DIALOG"),

    // alerts routes
    ROUTE_ALERT_NO_INTERNET("ROUTE_ALERT_NO_INTERNET"),
    ROUTE_ALERT_BE_DISCONNECTED("BE_DISCONNECTED_ALERT"),

    // notifications routes
    ;

}