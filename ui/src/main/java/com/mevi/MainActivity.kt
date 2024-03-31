package com.mevi

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mevi.ui.MainContainerLayout
import com.mevi.ui.internet.NetworkManager
import com.mevi.ui.startup.standard.StartupChainHandler
import com.mevi.ui.theme.MeviTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    val networkManager: NetworkManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            MeviTheme {
                MainContainerLayout()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        StartupChainHandler(this).execute()
        initializeNetworkManager()
    }

    override fun onStop() {
        super.onStop()
        releaseNetworkManager()
    }

    private fun initializeNetworkManager() {
        networkManager.registerNetworkCallbacks(::onInternetAvailable, ::onInternetUnavailable)
    }

    private fun releaseNetworkManager() {
        networkManager.unregisterNetworkCallbacks()
    }

    private fun onInternetAvailable() {
        Toast.makeText(this, "INTERNET is available", Toast.LENGTH_SHORT).show()
    }

    private fun onInternetUnavailable() {
        Toast.makeText(this, "INTERNET has been lost", Toast.LENGTH_SHORT).show()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MeviTheme {
        MainContainerLayout()
    }
}
