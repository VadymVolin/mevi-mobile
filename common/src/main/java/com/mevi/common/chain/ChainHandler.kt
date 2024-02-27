package com.mevi.common.chain

import com.mevi.common.chain.node.ChainNode
import java.util.LinkedList

/**
 * Base class for chain handlers
 * @see ChainNode
 *
 * @author Vadym Volin
 * @since 2/27/24
 */
abstract class ChainHandler(nodes: Array<ChainNode>) {

    private val chainNodes: LinkedList<ChainNode> = LinkedList<ChainNode>()

    init {
        buildChain(nodes)
    }

    private fun buildChain(nodes: Array<ChainNode>) {
        for (nodeIndex in nodes.indices) {
            val node = nodes[nodeIndex]
            node.next = nodes.elementAtOrNull(nodeIndex + 1)
            chainNodes.add(node)
        }
    }

    /**
     * Starts executing chain nodes
     */
    fun execute() {
        chainNodes.takeIf { it.isNotEmpty() }?.get(0)?.execute()
    }
}