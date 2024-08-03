package com.mevi.ui.navigation.host

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navigation
import com.mevi.ui.internet.NetworkManager
import com.mevi.ui.internet.NoInternetConnectionAlert
import com.mevi.ui.navigation.Graph
import com.mevi.ui.navigation.NavigationComponent
import com.mevi.ui.navigation.Route
import com.mevi.ui.screens.account.AccountScreen
import com.mevi.ui.screens.authorization.AuthorizationScreen
import com.mevi.ui.screens.authorization.AuthorizationViewModel
import com.mevi.ui.screens.call.RandomCallScreen
import com.mevi.ui.screens.chats.ChatsScreen
import com.mevi.ui.screens.loading.StartScreen
import com.mevi.ui.screens.loading.StartViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ScreenNavigationHost(
    modifier: Modifier = Modifier,
    networkManager: NetworkManager,
    navigationComponent: NavigationComponent,
    initializeGlobalNetworkCallback: () -> Unit,
    updateAppBarsVisibility: (Boolean) -> Unit
) = NavHost(
    navController = navigationComponent.getScreenNavController(),
    startDestination = Route.Startup.ROUTE_SCREEN_LOADING.route,
    exitTransition = { ExitTransition.None },
    popExitTransition = { ExitTransition.None },
    enterTransition = { EnterTransition.None },
    popEnterTransition = { EnterTransition.None },
    contentAlignment = Alignment.TopCenter,
    modifier = modifier
) {
    // start screen(first app real screen)
    composable(Route.Startup.ROUTE_SCREEN_LOADING.route) {
        updateAppBarsVisibility(false)
        val viewModel: StartViewModel = koinViewModel<StartViewModel>()
        val checkAuthorizationAction = remember {
            { viewModel.checkUserAuthorization() }
        }
        val onAuthorizedAction = remember {
            { navigationComponent.navigate(Graph.MAIN_APPLICATION_GRAPH) }
        }
        val onUnauthorizedAction = remember {
            { navigationComponent.navigate(Graph.AUTHORIZATION_GRAPH) }
        }
        val checkInternet = remember {
            { networkManager.isInternetConnectionAvailable() }
        }
        val onInternetUnavailable = remember {
            { callback: () -> Unit -> navigationComponent.navigate(Route.Dialog.ROUTE_ALERT_NO_INTERNET, callback) }
        }
        StartScreen(
            viewModel.accountState,
            checkInternet,
            onInternetUnavailable,
            checkAuthorizationAction,
            onAuthorizedAction,
            onUnauthorizedAction,
            initializeGlobalNetworkCallback
        )
    }

    // authorization nested graph
    navigation(
        startDestination = Route.Startup.ROUTE_AUTHORIZATION.route,
        route = Graph.AUTHORIZATION_GRAPH.route
    ) {
        // authorization screen
        composable(Route.Startup.ROUTE_AUTHORIZATION.route) {
            updateAppBarsVisibility(false)
            val viewModel: AuthorizationViewModel = it.sharedViewModel<AuthorizationViewModel>(navigationComponent.getScreenNavController())
            val loginAction = remember { viewModel::login }
            val loginByGoogleAction = remember { viewModel::loginByGoogle }
            val registrationAction = remember { viewModel::register }
            val forgotPasswordAction = remember {
                { navigationComponent.navigate(Route.Startup.ROUTE_FORGOT_PASSWORD) }
            }
            val onAuthenticated = remember {
                { navigationComponent.navigate(Graph.MAIN_APPLICATION_GRAPH) }
            }
            AuthorizationScreen(
                viewModel.loginState,
                loginByGoogleAction,
                loginAction,
                viewModel.registrationState,
                registrationAction,
                forgotPasswordAction,
                onAuthenticated
            )
        }
        // forgot password
        composable(Route.Startup.ROUTE_FORGOT_PASSWORD.route) {
            updateAppBarsVisibility(false)
            // TODO: CHANGE IT LATER
            val viewModel: AuthorizationViewModel = it.sharedViewModel<AuthorizationViewModel>(navigationComponent.getScreenNavController())
            Column {
                Text(text = viewModel.loginState.toString())
                Button(onClick = {
                    navigationComponent.getScreenNavController()
                        .navigate(Graph.MAIN_APPLICATION_GRAPH.route) {
                            popUpTo(Graph.AUTHORIZATION_GRAPH.route) {
                                inclusive = true
                            }
                        }
                }) {
                    Text(text = "TODO")
                }
            }
        }
    }

    // main navigation nested graph(bottom menu)
    navigation(
        startDestination = Route.Menu.ROUTE_SCREEN_CHATS.route,
        route = Graph.MAIN_APPLICATION_GRAPH.route
    ) {
        composable(Route.Menu.ROUTE_SCREEN_CHATS.route) {
            updateAppBarsVisibility(true)
            ChatsScreen()
        }
        composable(Route.Menu.ROUTE_SCREEN_RANDOM_CALL.route) {
            updateAppBarsVisibility(true)
            RandomCallScreen()
        }
        composable(Route.Menu.ROUTE_SCREEN_ACCOUNT.route) {
            updateAppBarsVisibility(true)
            AccountScreen {
                navigationComponent.navigate(Route.Startup.ROUTE_SCREEN_LOADING)
            }
        }
    }

    dialog(
        route = Route.Dialog.ROUTE_ALERT_NO_INTERNET.route,
        dialogProperties = DialogProperties(
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = true,
            dismissOnBackPress = false
        )
    ) {
        NoInternetConnectionAlert()
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navigationController: NavController) : T {
    val parentGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this) {
        navigationController.getBackStackEntry(parentGraphRoute)
    }
    return koinViewModel<T>(viewModelStoreOwner = parentEntry)
}