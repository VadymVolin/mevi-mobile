package com.mevi.ui.internet

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo

/**
 * Manager to track internet connection
 *
 * @author Vadym Volin
 * @since 2/27/24
 */
class NetworkManager(context: Context) {

    private val connectivityManager: ConnectivityManager = context.getSystemService(ConnectivityManager::class.java)

    private var networkCallback: ConnectivityManager.NetworkCallback? = null

    fun checkInternetConnection(): Boolean {
        val activeInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        return if (activeInfo?.isConnected == true) {
            activeInfo.type == ConnectivityManager.TYPE_WIFI || activeInfo.type == ConnectivityManager.TYPE_MOBILE
        } else {
            false
        }
    }

    fun registerNetworkCallbacks(onAvailable: () -> Unit, onUnavailable: () -> Unit) {
        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                onAvailable()
            }

            override fun onLost(network: Network) {
                onUnavailable()
            }

            override fun onUnavailable() {
                onUnavailable()
            }
        }
        networkCallback?.let {
            connectivityManager.registerDefaultNetworkCallback(it)
        }
    }

    fun unregisterNetworkCallbacks() {
        networkCallback?.let {
            connectivityManager.unregisterNetworkCallback(it)
        }
    }
}