package com.mevi.ui.navigation

import android.util.Log
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.mevi.ui.navigation.NavigationComponent.AlertNavigator
import com.mevi.ui.navigation.NavigationComponent.DialogNavigator
import com.mevi.ui.navigation.NavigationComponent.NotificationNavigator
import com.mevi.ui.navigation.NavigationComponent.ScreenNavigator

/**
 * Navigation component,
 * check also internal navigators: [AlertNavigator],[DialogNavigator],[NotificationNavigator],[ScreenNavigator];
 * and hosts:
 * [com.mevi.ui.navigation.host.NotificationNavigationHost],
 * [com.mevi.ui.navigation.host.AlertNavigationHost],
 * [com.mevi.ui.navigation.host.DialogNavigationHost],
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
    private lateinit var dialogNavigator: DialogNavigator
    private lateinit var alertNavigator: AlertNavigator
    private lateinit var notificationNavigator: NotificationNavigator

    internal fun initialize(
        screenNavController: NavHostController,
        dialogNavController: NavHostController,
        alertNavController: NavHostController,
        notificationNavController: NavHostController
    ) {
        screenNavigator = ScreenNavigator(screenNavController)
        dialogNavigator = DialogNavigator(dialogNavController)
        alertNavigator =  AlertNavigator(alertNavController)
        notificationNavigator = NotificationNavigator(notificationNavController)
    }

    fun showScreen(route: NavigationRoute) {
        screenNavigator.navigate(route)
    }

    fun showScreen(route: NavigationRoute, onClose: () -> Unit) {
        screenNavigator.navigate(route, onClose)
    }

    fun closeScreen() {
        screenNavigator.navigateBack()
    }

    fun closeScreen(route: NavigationRoute) {
        screenNavigator.navigateBack(route)
    }

    fun showDialog(route: NavigationRoute) {
        dialogNavigator.navigate(route)
    }

    fun showDialog(route: NavigationRoute, onClose: () -> Unit) {
        dialogNavigator.navigate(route, onClose)
    }

    fun closeDialog() {
        dialogNavigator.navigateBack()
    }

    fun closeDialog(route: NavigationRoute) {
        dialogNavigator.navigateBack(route)
    }

    fun showAlert(route: NavigationRoute) {
        alertNavigator.navigate(route)
    }

    fun showAlert(route: NavigationRoute, onClose: () -> Unit) {
        alertNavigator.navigate(route, onClose)
    }

    fun closeAlert() {
        alertNavigator.navigateBack()
    }

    fun closeAlert(route: NavigationRoute) {
        alertNavigator.navigateBack(route)
    }

    fun showNotification(route: NavigationRoute) {
        notificationNavigator.navigate(route)
    }

    fun showNotification(route: NavigationRoute, onClose: () -> Unit) {
        notificationNavigator.navigate(route, onClose)
    }

    fun closeNotification() {
        notificationNavigator.navigateBack()
    }

    fun closeNotification(route: NavigationRoute) {
        notificationNavigator.navigateBack(route)
    }

    fun getScreenNavController() = screenNavigator.getNavController()
    fun getDialogNavController() = dialogNavigator.getNavController()
    fun getAlertNavController() = alertNavigator.getNavController()
    fun getNotificationNavController() = notificationNavigator.getNavController()

    private interface Navigator {

        val navigationCallback: MutableMap<String, () -> Unit>
        fun getTag(): String
        fun getNavController(): NavHostController
        fun navigate(route: NavigationRoute)
        fun navigate(route: NavigationRoute, onClose: () -> Unit)

        fun navigateBack() {
            val destinationRoute = getNavController().currentDestination?.route
            val wasDestinationPopped = getNavController().popBackStack()
            if (wasDestinationPopped.and(destinationRoute != null)) {
                navigationCallback[destinationRoute]?.invoke()
            }
            Log.d(getTag(), "navigateBack, was popped: $wasDestinationPopped")
        }

        fun navigateBack(route: NavigationRoute) {
            val destinationRoute = getNavController().currentDestination?.route
            val wasDestinationPopped = getNavController().popBackStack(route.route, inclusive = true)
            if (wasDestinationPopped.and(destinationRoute != null).and(route.route == destinationRoute)) {
                navigationCallback[destinationRoute]?.invoke()
            }
            Log.d(getTag(), "navigateBack: ${route.route}, was popped: $wasDestinationPopped")
        }
    }

    private class ScreenNavigator(
        private val navController: NavHostController,
        override val navigationCallback: MutableMap<String, () -> Unit> = mutableMapOf()
    ) : Navigator {
        companion object {
            val TAG: String = NavigationComponent.TAG.plus(RIGHT_ARROW).plus(ScreenNavigator::class.java.name)
        }

        override fun getTag(): String = TAG

        override fun getNavController(): NavHostController = navController

        override fun navigate(route: NavigationRoute) {
            Log.d(TAG, "navigate: ${route.route}")
            val currentDestination = navController.currentBackStackEntry?.destination
            if (currentDestination?.hierarchy?.any { it.route == route.route } != true) {
                navController.navigate(route.route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // re-selecting the same item
                    launchSingleTop = true
                    // Restore state when re-selecting a previously selected item
                    restoreState = true
                }
            }
        }

        override fun navigate(route: NavigationRoute, onClose: () -> Unit) {
            navigationCallback[route.route] = onClose
            navigate(route)
        }
    }

    private class DialogNavigator(
        private val navController: NavHostController,
        override val navigationCallback: MutableMap<String, () -> Unit> = mutableMapOf()
    ) : Navigator {
        companion object {
            val TAG: String = NavigationComponent.TAG.plus(RIGHT_ARROW).plus(DialogNavigator::class.java.name)
        }

        override fun getTag(): String = TAG

        override fun getNavController(): NavHostController = navController

        override fun navigate(route: NavigationRoute) {
            Log.d(TAG, "navigate: ${route.route}")
            val currentDestination = navController.currentBackStackEntry?.destination
            if (currentDestination?.hierarchy?.any { it.route == route.route } != true) {
                if (route.removeLastRoute) {
                    navController.navigateUp()
                }
                navController.navigate(route.route) {
                    // Avoid multiple copies of the same destination when
                    // re-selecting the same item
                    launchSingleTop = true
                    // Restore state when re-selecting a previously selected item
                    restoreState = true
                }
            }
        }

        override fun navigate(route: NavigationRoute, onClose: () -> Unit) {
            navigationCallback[route.route] = onClose
            navigate(route)
        }
    }

    private class AlertNavigator(
        private val navController: NavHostController,
        override val navigationCallback: MutableMap<String, () -> Unit> = mutableMapOf()
    ) : Navigator {
        companion object {
            val TAG: String = NavigationComponent.TAG.plus(RIGHT_ARROW).plus(AlertNavigator::class.java.name)
        }

        override fun getTag(): String = TAG

        override fun getNavController(): NavHostController = navController

        override fun navigate(route: NavigationRoute) {
            Log.d(TAG, "navigate: ${route.route}")
            val currentDestination = navController.currentBackStackEntry?.destination
            if (currentDestination?.hierarchy?.any { it.route == route.route } != true) {
                if (route.removeLastRoute) {
                    navController.navigateUp()
                }
                navController.navigate(route.route) {
                    // Avoid multiple copies of the same destination when
                    // re-selecting the same item
                    launchSingleTop = true
                    // Restore state when re-selecting a previously selected item
                    restoreState = true
                }
            }
        }

        override fun navigate(route: NavigationRoute, onClose: () -> Unit) {
            navigationCallback[route.route] = onClose
            navigate(route)
        }
    }

    private class NotificationNavigator(
        private val navController: NavHostController,
        override val navigationCallback: MutableMap<String, () -> Unit> = mutableMapOf()
    ) : Navigator {

        companion object {
            val TAG: String = NavigationComponent.TAG.plus(RIGHT_ARROW).plus(NotificationNavigator::class.java.name)
        }

        override fun getTag(): String = TAG

        override fun getNavController(): NavHostController = navController

        override fun navigate(route: NavigationRoute) {
            Log.d(TAG, "navigate: ${route.route}")
            val currentDestination = navController.currentBackStackEntry?.destination
            if (currentDestination?.hierarchy?.any { it.route == route.route } != true) {
                while (navController.popBackStack()) {
                    Log.d(TAG, "remove previous notification")
                }
                navController.navigate(route.route) {
                    // Avoid multiple copies of the same destination when
                    // re-selecting the same item
                    launchSingleTop = true
                    // Restore state when re-selecting a previously selected item
                    restoreState = true
                }
            }
        }

        override fun navigate(route: NavigationRoute, onClose: () -> Unit) {
            navigationCallback[route.route] = onClose
            navigate(route)
        }
    }

}