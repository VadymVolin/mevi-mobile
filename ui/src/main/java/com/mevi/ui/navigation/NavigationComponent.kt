package com.mevi.ui.navigation

import android.content.Context
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

class NavigationComponent(private val context: Context) {

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

    fun popUpScreen() {
        screenNavigator.getNavController().popBackStack()
    }

    fun showDialog(route: NavigationRoute) {
        dialogNavigator.navigate(route)
    }

    fun popUpDialog() {
        dialogNavigator.getNavController().popBackStack()
    }

    fun showAlert(route: NavigationRoute) {
        alertNavigator.navigate(route)
    }

    fun popUpAlert() {
        alertNavigator.getNavController().popBackStack()
    }

    fun showNotification(route: NavigationRoute) {
        notificationNavigator.navigate(route)
    }

    fun popUpNotification() {
        notificationNavigator.getNavController().popBackStack()
    }

    fun getScreenNavController() = screenNavigator.getNavController()
    fun getDialogNavController() = dialogNavigator.getNavController()
    fun getAlertNavController() = alertNavigator.getNavController()
    fun getNotificationNavController() = notificationNavigator.getNavController()

    private interface Navigator {
        fun getNavController(): NavHostController
        fun navigate(route: NavigationRoute)
    }

    class ScreenNavigator(private val navController: NavHostController) : Navigator {

        override fun getNavController(): NavHostController = navController

        override fun navigate(route: NavigationRoute) {
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

    class DialogNavigator(private val navController: NavHostController) : Navigator {
        override fun getNavController(): NavHostController = navController

        override fun navigate(route: NavigationRoute) {
        }
    }

    class AlertNavigator(private val navController: NavHostController) : Navigator {
        override fun getNavController(): NavHostController = navController

        override fun navigate(route: NavigationRoute) {
        }
    }

    class NotificationNavigator(private val navController: NavHostController) : Navigator {
        override fun getNavController(): NavHostController = navController

        override fun navigate(route: NavigationRoute) {
        }
    }

}