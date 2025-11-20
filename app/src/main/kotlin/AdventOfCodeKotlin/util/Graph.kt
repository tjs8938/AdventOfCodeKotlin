package AdventOfCodeKotlin.util

class Graph<V, T: Node<V>>(val width: Int, val height: Int) : HashMap<Pair<Int, Int>, T>() {
    companion object {

        fun <V, T: Node<V>> buildGraph(
            input: List<List<V>>,
            initialize: (Int, Int, V) -> T,
            includePredicate: (V) -> Boolean = { true },
            callbacks: Map<V, (V, T) -> Unit> = mapOf()
        ): Graph<V, T> {
            val allNodes = Graph<V, T>(input[0].size, input.size)

            input.forEachIndexed { rowIndex, row ->
                row.forEachIndexed { colIndex, cell ->
                    if (includePredicate(cell)) {
                        val node = initialize(colIndex, rowIndex, cell)
                        allNodes[rowIndex to colIndex] = node
                        allNodes[rowIndex to colIndex - 1]?.let { node.addNeighbor(it) }
                        allNodes[rowIndex - 1 to colIndex]?.let { node.addNeighbor(it) }
                        callbacks[cell]?.let { it(cell, node) }
                    }
                }
            }

            return allNodes
        }
    }

}

open class Node<V>(val x: Int, val y: Int, val value: V) : Adjacent() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Node<V>

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }

    override fun toString(): String {
        return "Node(x=$x, y=$y, label=$value)"
    }

    fun getNeighborNodes() : List<Node<V>> {
        return neighbors.map { it as Node<V> }
    }
}

open class Node3d(x: Int, y: Int, val z: Int, label: Char) : Node<Char>(x, y, label) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Node3d

        if (x != other.x) return false
        if (y != other.y) return false
        if (z != other.z) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        result = 31 * result + z
        return result
    }

    override fun toString(): String {
        return "Node3d(x=$x, y=$y, z=$z, label=$value)"
    }
}