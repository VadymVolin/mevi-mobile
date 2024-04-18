package com.mevi.ui.navigation

import com.mevi.common.translations.TextKey

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
    val nameKey: TextKey?,
    val removeLastRoute: Boolean = false
) {
    // main screen routes
    ROUTE_SCREEN_CHATS("CHATS", TextKey.TEXT_CHATS_ROUTE),
    ROUTE_SCREEN_RANDOM_CALL("RANDOM_CALL", TextKey.TEXT_RANDOM_CALL_ROUTE),
    ROUTE_SCREEN_ACCOUNT("ACCOUNT", TextKey.TEXT_ACCOUNT_ROUTE),

    // dialog routes
    ROUTE_DIALOG_LOGIN_DIALOG("LOGIN_DIALOG", null),
    ROUTE_DIALOG_REGISTRATION_DIALOG("REGISTRATION_DIALOG", null),

    // alerts routes
    ROUTE_ALERT_BE_DISCONNECTED("BE_DISCONNECTED_ALERT", null),

    // notifications routes
    ROUTE_NOTIFICATION_NO_INTERNET("NO_INTERNET_NOTIFICATION", null),
    ;

}