package com.mevi

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.mevi.ui.AppLayout
import com.mevi.ui.internet.NetworkManager
import com.mevi.ui.navigation.NavigationComponent
import com.mevi.ui.navigation.NavigationGraphRoute
import com.mevi.ui.startup.standard.OnboardingChaneHandler
import com.mevi.ui.theme.MeviTheme
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
class MainActivity : ComponentActivity() {

    companion object {
        val TAG: String = MainActivity::class.java.name
    }

    private val networkManager: NetworkManager by inject()
    private val navigationComponent: NavigationComponent by inject()
    private val onboardingChaneHandler: OnboardingChaneHandler by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        onboardingChaneHandler.setFinalNode {
            initializeNetworkCallback()
        }
        setContent {
            KoinAndroidContext() {
                MeviTheme {
                    navigationComponent.initialize(rememberNavController())
                    AppLayout(
                        navigationComponent,
                        onboardingChaneHandler
                    )
                }
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
            navigationComponent.navigate(NavigationGraphRoute.ROUTE_ALERT_NO_INTERNET)
        }
    }
}