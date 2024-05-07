package com.mevi.ui.internet

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.util.Log

/**
 * Manager to track internet connection
 *
 * @author Vadym Volin
 * @since 2/27/24
 */
class NetworkManager(context: Context) {

    companion object {
        private val TAG: String = NetworkManager::class.java.name
    }

    private val connectivityManager: ConnectivityManager = context.getSystemService(ConnectivityManager::class.java)

    private val networkCallbacks = mutableMapOf<String, ConnectivityManager.NetworkCallback>()

    private val mainNetworkCallback: ConnectivityManager.NetworkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            Log.d(TAG, "Network connection is available")
            networkCallbacks.values.forEach {
                it.onAvailable(network)
            }
        }

        override fun onLost(network: Network) {
            Log.d(TAG, "Network connection is lost")
            networkCallbacks.values.forEach {
                it.onLost(network)
            }
        }

        override fun onUnavailable() {
            Log.d(TAG, "Network connection is unavailable")
            networkCallbacks.values.forEach {
                it.onUnavailable()
            }
        }
    }

    init {
        connectivityManager.registerDefaultNetworkCallback(mainNetworkCallback)
    }

    fun isInternetConnectionAvailable(): Boolean {
        return connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.let {
            it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || it.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)
                    || it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                    || it.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
        } ?: false
    }

    fun registerNetworkCallbacks(callbackTag: String, onAvailable: (() -> Unit)?, onUnavailable: (() -> Unit)?) {
        Log.d(TAG, "Add network callback for: $callbackTag")
        networkCallbacks[callbackTag] = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                onAvailable?.invoke()
            }

            override fun onLost(network: Network) {
                onUnavailable?.invoke()
            }

            override fun onUnavailable() {
                onUnavailable?.invoke()
            }
        }
    }

    fun unregisterNetworkCallbacks(callbackTag: String) {
        Log.d(TAG, "Remove network callback for: $callbackTag")
        networkCallbacks.remove(callbackTag)
    }
}