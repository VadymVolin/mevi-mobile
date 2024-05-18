package com.mevi.ui.navigation

import android.util.Log
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import com.mevi.ui.navigation.NavigationComponent.ScreenNavigator

/**
 * Navigation component,
 * check also internal navigators: [ScreenNavigator];
 * and hosts:
 * [com.mevi.ui.navigation.host.ScreenNavigationHost];
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 4/20/24
 */
class NavigationComponent {

    companion object {
        val TAG: String = NavigationComponent::class.java.name
        const val RIGHT_ARROW: String = "->"
    }

    private lateinit var screenNavigator: ScreenNavigator

    internal fun initialize(
        screenNavController: NavHostController
    ) {
        screenNavigator = ScreenNavigator(screenNavController)
    }

    fun navigate(route: Route) {
        screenNavigator.navigate(route)
    }

    fun navigate(route: Route, onClose: () -> Unit) {
        screenNavigator.navigate(route, onClose)
    }

    fun navigate(route: Graph) {
        screenNavigator.navigate(route)
    }

    fun navigate(route: Graph, onClose: () -> Unit) {
        screenNavigator.navigate(route, onClose)
    }

    fun closeScreen() {
        screenNavigator.navigateBack()
    }

    fun closeScreen(route: Route) {
        screenNavigator.navigateBack(route)
    }

    fun getScreenNavController() = screenNavigator.getNavController()

    private interface Navigator {

        val navigationCallback: MutableMap<String, () -> Unit>
        fun getTag(): String
    }

    private class ScreenNavigator(
        private val navController: NavHostController,
        override val navigationCallback: MutableMap<String, () -> Unit> = mutableMapOf()
    ) : Navigator {
        companion object {
            val TAG: String =
                NavigationComponent.TAG.plus(RIGHT_ARROW).plus(ScreenNavigator::class.java.name)
        }

        fun navigateBack() {
            val destinationRoute = getNavController().currentDestination?.route
            val wasDestinationPopped = getNavController().popBackStack()
            if (wasDestinationPopped.or(destinationRoute != null)) {
                navigationCallback[destinationRoute]?.invoke()
            }
            Log.d(getTag(), "navigateBack, was popped: $wasDestinationPopped")
        }

        fun navigateBack(route: Route) {
            val destinationRoute = getNavController().currentDestination?.route
            val wasDestinationPopped =
                getNavController().popBackStack(route.route, inclusive = true)
            if (wasDestinationPopped.or(destinationRoute != null)
                    .and(route.route == destinationRoute)
            ) {
                navigationCallback[destinationRoute]?.invoke()
            }
            Log.d(getTag(), "navigateBack: ${route.route}, was popped: $wasDestinationPopped")
        }

        override fun getTag(): String = TAG

        fun getNavController(): NavHostController = navController

        fun navigate(route: Route) {
            Log.d(TAG, "navigate: ${route.route}")
            val currentDestination = navController.currentDestination
            if (currentDestination?.hierarchy?.any { it.route == route.route } != true) {
                navController.navigate(route.route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    if (route.removeLastRoute) {
                        when (route) {
                            Route.Menu.ROUTE_SCREEN_CHATS, Route.Menu.ROUTE_SCREEN_RANDOM_CALL,
                            Route.Menu.ROUTE_SCREEN_ACCOUNT, Route.Startup.ROUTE_AUTHORIZATION -> {
                                popUpTo(0) {
                                    inclusive = true
                                }
                            }

                            else -> {
                                currentDestination?.route?.let {
                                    popUpTo(it) {
                                        inclusive = true
                                    }
                                }
                            }
                        }

                    }
                    // Avoid multiple copies of the same destination when
                    // re-selecting the same item
                    launchSingleTop = true
                    // Restore state when re-selecting a previously selected item
                    restoreState = true
                }
            }
        }

        fun navigate(route: Graph) {
            Log.d(TAG, "navigate: ${route.route}")
            val currentDestination = navController.currentDestination
            if (currentDestination?.hierarchy?.any { it.route == route.route } != true) {
                navController.navigate(route.route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(0) {
                        inclusive = true
                    }
                    // Avoid multiple copies of the same destination when
                    // re-selecting the same item
                    launchSingleTop = true
                    // Restore state when re-selecting a previously selected item
                    restoreState = true
                }
            }
        }

        fun navigate(route: Route, onClose: () -> Unit) {
            navigationCallback[route.route] = onClose
            navigate(route)
        }

        fun navigate(route: Graph, onClose: () -> Unit) {
            navigationCallback[route.route] = onClose
            navigate(route)
        }
    }

}