package com.mevi

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mevi.ui.internet.NetworkManager
import com.mevi.ui.startup.standard.StartupChainHandler
import com.mevi.ui.theme.MeviTheme

class MainActivity : ComponentActivity() {

    var networkManager: NetworkManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            MeviTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                }
            }
        }
        networkManager = NetworkManager(getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
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
        networkManager?.registerNetworkCallbacks(::onInternetAvailable, ::onInternetUnavailable)
    }

    private fun releaseNetworkManager() {
        networkManager?.unregisterNetworkCallbacks()
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
    }
}
