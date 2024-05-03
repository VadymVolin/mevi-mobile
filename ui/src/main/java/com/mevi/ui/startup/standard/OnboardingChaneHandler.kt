package com.mevi.ui.startup.standard

import com.mevi.common.chain.ChainHandler
import com.mevi.ui.internet.NetworkManager
import com.mevi.ui.navigation.NavigationComponent
import com.mevi.ui.startup.standard.node.AuthorizationNode
import com.mevi.ui.startup.standard.node.CheckInternetNode

/**
 * The standard startup chain,
 * should be called when on base application startup
 *
 * @author Vadym Volin
 * @author midnight85
 * @since 2/27/24
 */
class OnboardingChaneHandler(
    navigator: NavigationComponent,
    networkManager: NetworkManager
) : ChainHandler(
    arrayOf(
        CheckInternetNode(navigator, networkManager),
        AuthorizationNode(navigator)
    )
)