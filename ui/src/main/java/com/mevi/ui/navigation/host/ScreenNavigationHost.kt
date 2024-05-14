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
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navigation
import com.mevi.ui.internet.NoInternetConnectionAlert
import com.mevi.ui.navigation.NavigationComponent
import com.mevi.ui.navigation.NavigationGraphRoute
import com.mevi.ui.navigation.NavigationRoute
import com.mevi.ui.screens.account.AccountScreen
import com.mevi.ui.screens.authorization.AuthorizationScreen
import com.mevi.ui.screens.authorization.AuthorizationViewModel
import com.mevi.ui.screens.call.RandomCallScreen
import com.mevi.ui.screens.chats.ChatsScreen
import com.mevi.ui.screens.loading.StartScreen
import com.mevi.ui.screens.loading.StartViewModel
import com.mevi.ui.startup.standard.OnboardingChaneHandler
import org.koin.androidx.compose.koinViewModel

@Composable
fun ScreenNavigationHost(
    modifier: Modifier = Modifier,
    navigationComponent: NavigationComponent,
    onboardingChaneHandler: OnboardingChaneHandler,
    updateAppBarsVisibility: (Boolean) -> Unit
) = NavHost(
    navController = navigationComponent.getScreenNavController(),
    startDestination = NavigationGraphRoute.ROUTE_SCREEN_LOADING.route,
    exitTransition = { ExitTransition.None },
    popExitTransition = { ExitTransition.None },
    enterTransition = { EnterTransition.None },
    popEnterTransition = { EnterTransition.None },
    contentAlignment = Alignment.TopCenter,
    modifier = modifier
) {
    // start screen(first app real screen)
    composable(NavigationGraphRoute.ROUTE_SCREEN_LOADING.route) {
        updateAppBarsVisibility(false)
        val viewModel: StartViewModel = koinViewModel<StartViewModel>()
        val checkAuthorizationAction = remember {
            {
                viewModel.checkUserAuthorization()
            }
        }
        val onAuthorizedAction = remember {
            {
                navigationComponent.navigate(NavigationRoute.MAIN_APPLICATION_GRAPH)
            }
        }
        val onUnauthorizedAction = remember {
            {
                navigationComponent.navigate(NavigationRoute.AUTHORIZATION_GRAPH)
            }
        }
        StartScreen(viewModel.accountState, checkAuthorizationAction, onAuthorizedAction, onUnauthorizedAction, startStartup = {
            onboardingChaneHandler.execute()
        })
    }

    // authorization nested graph
    navigation(
        startDestination = NavigationGraphRoute.ROUTE_AUTHORIZATION.route,
        route = NavigationRoute.AUTHORIZATION_GRAPH.route
    ) {
        // authorization screen
        composable(NavigationGraphRoute.ROUTE_AUTHORIZATION.route) {
            updateAppBarsVisibility(false)
            val viewModel: AuthorizationViewModel = it.sharedViewModel<AuthorizationViewModel>(navigationComponent.getScreenNavController())
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
            val forgotPasswordAction = remember {
                {
                    navigationComponent.navigate(NavigationGraphRoute.ROUTE_FORGOT_PASSWORD)
                }
            }
            AuthorizationScreen(viewModel.loginState, loginAction, viewModel.registrationState, registrationAction, forgotPasswordAction)
        }
        // forgot password
        composable(NavigationGraphRoute.ROUTE_FORGOT_PASSWORD.route) {
            updateAppBarsVisibility(false)
            // TODO: CHANGE IT LATER
            val viewModel: AuthorizationViewModel = it.sharedViewModel<AuthorizationViewModel>(navigationComponent.getScreenNavController())
            Column {
                Text(text = viewModel.loginState.toString())
                Button(onClick = {
                    navigationComponent.getScreenNavController()
                        .navigate(NavigationRoute.MAIN_APPLICATION_GRAPH.route) {
                            popUpTo(NavigationRoute.AUTHORIZATION_GRAPH.route) {
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
        startDestination = NavigationGraphRoute.ROUTE_SCREEN_CHATS.route,
        route = NavigationRoute.MAIN_APPLICATION_GRAPH.route
    ) {
        composable(NavigationGraphRoute.ROUTE_SCREEN_CHATS.route) {
            updateAppBarsVisibility(true)
            ChatsScreen()
        }
        composable(NavigationGraphRoute.ROUTE_SCREEN_RANDOM_CALL.route) {
            updateAppBarsVisibility(true)
            RandomCallScreen()
        }
        composable(NavigationGraphRoute.ROUTE_SCREEN_ACCOUNT.route) {
            updateAppBarsVisibility(true)
            AccountScreen()
        }
    }

    dialog(NavigationGraphRoute.ROUTE_ALERT_NO_INTERNET.route) {
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