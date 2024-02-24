package com.mevi.common.chain

import com.mevi.common.chain.model.ChainNode
import java.util.LinkedList

class ChainHandler(nodes: Array<ChainNode>) {

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

    fun execute() {

    }
}