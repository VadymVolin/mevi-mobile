package com.mevi.ui.startup.standard

import com.mevi.MainActivity
import com.mevi.common.chain.ChainHandler
import com.mevi.ui.startup.standard.node.CheckInternetNode

/**
 * The standard startup chain,
 * should be called when on base application startup
 *
 * @author Vadym Volin
 * @since 2/27/24
 */
class StartupChainHandler(activity: MainActivity) : ChainHandler(
    arrayOf(
        CheckInternetNode(activity)
    )
)