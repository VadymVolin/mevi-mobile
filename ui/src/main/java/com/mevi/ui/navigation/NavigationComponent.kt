package com.mevi.ui.navigation

import android.util.Log
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

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

    fun closeScreen() {
        screenNavigator.navigateBack()
    }

    fun closeScreen(route: NavigationRoute) {
        screenNavigator.navigateBack(route)
    }

    fun showDialog(route: NavigationRoute) {
        dialogNavigator.navigate(route)
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

    fun closeAlert() {
        alertNavigator.navigateBack()
    }

    fun closeAlert(route: NavigationRoute) {
        alertNavigator.navigateBack(route)
    }

    fun showNotification(route: NavigationRoute) {
        notificationNavigator.navigate(route)
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
        fun getTag(): String
        fun getNavController(): NavHostController
        fun navigate(route: NavigationRoute)

        fun navigateBack() {
            val wasDestinationPopped = getNavController().popBackStack()
            Log.d(getTag(), "navigateBack, was popped: $wasDestinationPopped")
        }

        fun navigateBack(route: NavigationRoute) {
            val wasDestinationPopped = getNavController().popBackStack(route.route, inclusive = true)
            Log.d(getTag(), "navigateBack: ${route.route}, was popped: $wasDestinationPopped")
        }
    }

    private class ScreenNavigator(private val navController: NavHostController) : Navigator {
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
    }

    private class DialogNavigator(private val navController: NavHostController) : Navigator {
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
    }

    private class AlertNavigator(private val navController: NavHostController) : Navigator {
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
    }

    private class NotificationNavigator(private val navController: NavHostController) : Navigator {

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
    }

}