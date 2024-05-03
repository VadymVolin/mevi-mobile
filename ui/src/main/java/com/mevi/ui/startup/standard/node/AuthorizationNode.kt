package com.mevi.ui.startup.standard.node

import com.mevi.common.chain.node.ChainNode
import com.mevi.ui.navigation.NavigationComponent
import com.mevi.ui.navigation.NavigationRoute

class AuthorizationNode(
    private val navigationComponent: NavigationComponent
) : ChainNode() {
    override fun execute() {
        navigationComponent.showDialog(NavigationRoute.ROUTE_DIALOG_AUTHORIZATION_DIALOG, ::complete)
    }
}