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
        Toast.makeText(activity, "CheckInternetNode is started", Toast.LENGTH_SHORT).show()
        complete()
    }
}