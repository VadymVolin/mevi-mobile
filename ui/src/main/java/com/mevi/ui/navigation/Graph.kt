package com.mevi.ui.navigation

/**
 * Navigation route for nested graph,
 * @see Route
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 12/5/24
 */
enum class Graph(
    val route: String
) {
    AUTHORIZATION_GRAPH("AUTHORIZATION_GRAPH"),
    MAIN_APPLICATION_GRAPH("MAIN_GRAPH"),
}