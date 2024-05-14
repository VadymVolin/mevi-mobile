package com.mevi.ui.navigation

/**
 * Navigation route in nested graphs,
 * @see com.mevi.ui.navigation.host.ScreenNavigationHost
 * @see NavigationRoute
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 3/2/24
 */
enum class NavigationGraphRoute(
    val route: String,
    val removeLastRoute: Boolean = false
) {
    ROUTE_SCREEN_LOADING("LOADING"),
    // main screen routes
    ROUTE_SCREEN_CHATS("CHATS"),
    ROUTE_SCREEN_RANDOM_CALL("RANDOM_CALL"),
    ROUTE_SCREEN_ACCOUNT("ACCOUNT"),

    // auth routes
    ROUTE_AUTHORIZATION("AUTHORIZATION"),
    ROUTE_FORGOT_PASSWORD("FORGOT_PASSWORD"),

    // alerts routes
    ROUTE_ALERT_NO_INTERNET("ROUTE_ALERT_NO_INTERNET"),
    ROUTE_ALERT_BE_DISCONNECTED("BE_DISCONNECTED_ALERT"),

    // notifications routes
    ;
}