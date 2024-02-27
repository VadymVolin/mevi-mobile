package com.mevi.common.chain.node

/**
 * Base class for a Node chain instance
 * @see com.mevi.common.chain.ChainHandler
 */
abstract class ChainNode {
    var next: ChainNode? = null
        internal set

    /**
     * executes current chain Node
     */
    abstract fun execute()

    /**
     * Successfully completes the current Node of the chain
     * and switches to the next one
     */
    fun complete() {
        onComplete()
        next?.execute()
    }

    /**
     * Unsuccessfully completes the current Node of the chain
     * and switches to the next one
     */
    fun fail() {
        onFail()
        return
    }

    /**
     * Optional callback method will be called
     * when the chain completes successfully
     * @see complete
     */
    open fun onComplete() = Unit

    /**
     * Optional callback method will be called
     * when the chain completes unsuccessfully
     * @see fail
     */
    open fun onFail() = Unit
}