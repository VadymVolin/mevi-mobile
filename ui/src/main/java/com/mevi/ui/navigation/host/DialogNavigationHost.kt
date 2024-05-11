package com.mevi.ui.navigation.host

import android.util.Log
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mevi.ui.navigation.NavigationComponent
import com.mevi.ui.navigation.NavigationRoute
import com.mevi.ui.screens.authorization.AuthorizationScreen
import com.mevi.ui.screens.authorization.AuthorizationViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun DialogNavigationHost(
    modifier: Modifier = Modifier,
    navigationComponent: NavigationComponent
) = NavHost(
    navController = navigationComponent.getDialogNavController(),
    startDestination = NavigationRoute.ROUTE_EMPTY.route,
    exitTransition = { ExitTransition.None },
    popExitTransition = { ExitTransition.None },
    enterTransition = { EnterTransition.None },
    popEnterTransition = { EnterTransition.None },
    contentAlignment = Alignment.TopCenter,
    modifier = modifier
) {
    composable(NavigationRoute.ROUTE_EMPTY.route) {
        Log.d("DialogNavigationHost", "Initial empty composable")
    }
    composable(NavigationRoute.ROUTE_DIALOG_AUTHORIZATION_DIALOG.route) {
        val viewModel: AuthorizationViewModel = koinViewModel<AuthorizationViewModel>()
        val loginAction = remember {
            { credentials: Pair<String, String> ->
                viewModel.login(credentials)
            }
        }
        val registrationAction = remember {
            { credentials: Pair<String, String> ->
                viewModel.register(credentials)
            }
        }
        AuthorizationScreen(viewModel.loginState, loginAction, viewModel.registrationState, registrationAction)
    }
}