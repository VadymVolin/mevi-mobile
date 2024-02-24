package com.mevi.common.chain.model

/**
 *
 */
abstract class ChainNode {
    var next: ChainNode? = null
        internal set(value) {
            field = value
        }

    fun execute() {}

    fun complete() {
        next?.execute()
    }
    abstract fun cancel()
}