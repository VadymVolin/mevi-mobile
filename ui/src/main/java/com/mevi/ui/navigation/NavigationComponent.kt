package com.mevi.ui.navigation

import android.content.Context
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

class NavigationComponent(private val context: Context) {

    private lateinit var screenNavController: NavHostController
    private lateinit var dialogNavController: NavHostController
    private lateinit var alertNavController: NavHostController
    private lateinit var notificationNavController: NavHostController

    internal fun initialize(
        screenNavController: NavHostController,
        dialogNavController: NavHostController,
        alertNavController: NavHostController,
        notificationNavController: NavHostController
    ) {
        this.screenNavController = screenNavController
        this.dialogNavController = dialogNavController
        this.alertNavController = alertNavController
        this.notificationNavController = notificationNavController
    }

    fun handleMenuItemSelection(
        currentDestination: NavDestination?,
        item: NavigationRoute,
        navigationController: NavHostController
    ) {
        if (currentDestination?.hierarchy?.any { it.route == item.route } != true) {
            navigationController.navigate(item.route) {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(navigationController.graph.findStartDestination().id) {
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



    private interface Navigator {
        fun navigate()
    }
}