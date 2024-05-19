package com.mevi.ui.navigation

/**
 * Navigation route in nested graphs,
 * @see com.mevi.ui.navigation.host.ScreenNavigationHost
 * @see Graph
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 3/2/24
 */
sealed interface Route {
    val route: String
    val removeLastRoute: Boolean

    /**
     * Main screen routes
     */
    enum class Menu(
        override val route: String,
        override val removeLastRoute: Boolean
    ) : Route {
        ROUTE_SCREEN_CHATS("CHATS", true),
        ROUTE_SCREEN_RANDOM_CALL("RANDOM_CALL", true),
        ROUTE_SCREEN_ACCOUNT("ACCOUNT", true),
    }

    /**
     * Startup screen routes
     */
    enum class Startup(
        override val route: String,
        override val removeLastRoute: Boolean
    ) : Route {
        ROUTE_SCREEN_LOADING("LOADING", true),
        // auth routes
        ROUTE_AUTHORIZATION("AUTHORIZATION", true),
        ROUTE_FORGOT_PASSWORD("FORGOT_PASSWORD", false),
    }

    /**
     * Dialog routes
     */
    enum class Dialog(
        override val route: String,
        override val removeLastRoute: Boolean
    ) : Route {
        ROUTE_ALERT_NO_INTERNET("ROUTE_ALERT_NO_INTERNET", false),
        ROUTE_ALERT_BE_DISCONNECTED("BE_DISCONNECTED_ALERT", false),
    }

}