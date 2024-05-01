package com.mevi.ui.startup.standard.node

import android.util.Log
import com.mevi.common.chain.node.ChainNode
import com.mevi.ui.internet.NetworkManager
import com.mevi.ui.navigation.NavigationComponent
import com.mevi.ui.navigation.NavigationRoute

/**
 * Checks internet connection
 *
 * @author Vadym Volin
 * @author midnight85
 *
 * @since 2/27/24
 */
class CheckInternetNode(
    private val navigationComponent: NavigationComponent,
    private val networkManager: NetworkManager
) : ChainNode() {

    companion object {
        val TAG: String = CheckInternetNode::class.java.name
    }

    override fun execute() {
        if (networkManager.isInternetConnectionAvailable()) {
            complete()
        } else {
            navigationComponent.showAlert(NavigationRoute.ROUTE_ALERT_NO_INTERNET, ::complete)
        }
    }

    override fun onComplete() {
        super.onComplete()
        networkManager.unregisterNetworkCallbacks(TAG)
    }
}