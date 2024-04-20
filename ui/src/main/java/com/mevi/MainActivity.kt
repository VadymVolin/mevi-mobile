package com.mevi

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.mevi.ui.MainContainerLayout
import com.mevi.ui.internet.NetworkManager
import com.mevi.ui.navigation.NavigationComponent
import com.mevi.ui.navigation.NavigationRoute
import com.mevi.ui.startup.standard.OnboardingChaneHandler
import com.mevi.ui.theme.MeviTheme
import com.mevi.ui.translations.TextMatcher
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    companion object {
        val TAG: String = MainActivity::class.java.name
    }

    private val networkManager: NetworkManager by inject()
    private val textMatcher: TextMatcher by inject()
    private val navigationComponent: NavigationComponent by inject()
    private val onboardingChaneHandler: OnboardingChaneHandler by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            MeviTheme {
                navigationComponent.initialize(rememberNavController(), rememberNavController(), rememberNavController(), rememberNavController())
                onboardingChaneHandler.setFinalNode {
                    initializeNetworkCallback()
                }
                MainContainerLayout(navigationComponent, textMatcher)
                onboardingChaneHandler.execute()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        releaseNetworkManager()
    }

    private fun initializeNetworkCallback() {
        networkManager.registerNetworkCallbacks(TAG, ::onInternetAvailable, ::onInternetUnavailable)
    }

    private fun releaseNetworkManager() {
        networkManager.unregisterNetworkCallbacks(TAG)
    }

    private fun onInternetAvailable() {
        Log.d(TAG, "Internet connection has been restored")
    }

    private fun onInternetUnavailable() {
        runOnUiThread {
            Log.d(TAG, "Internet connection has been lost")
            navigationComponent.showAlert(NavigationRoute.ROUTE_ALERT_NO_INTERNET)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MeviTheme {
    }
}
