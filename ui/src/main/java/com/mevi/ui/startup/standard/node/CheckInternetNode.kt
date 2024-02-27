package com.mevi.ui.startup.standard.node

import android.widget.Toast
import com.mevi.MainActivity
import com.mevi.common.chain.node.ChainNode

/**
 * Checks internet connection
 *
 * @author Vadym Volin
 * @since 2/27/24
 */
class CheckInternetNode(private val activity: MainActivity) : ChainNode() {
    override fun execute() {
        if (activity.networkManager?.checkInternetConnection() == true) {
            Toast.makeText(activity, "CheckInternetNode has been started and internet is available", Toast.LENGTH_SHORT).show()
            complete()
        } else {
            Toast.makeText(activity, "CheckInternetNode has been started and internet is not available", Toast.LENGTH_SHORT).show()
            fail()
        }
    }

    override fun onComplete() {
        super.onComplete()
        Toast.makeText(activity, "CheckInternetNode has been finished successfully", Toast.LENGTH_SHORT).show()
    }

    override fun onFail() {
        super.onFail()
        Toast.makeText(activity, "CheckInternetNode has been finished unsuccessfully", Toast.LENGTH_SHORT).show()
    }
}