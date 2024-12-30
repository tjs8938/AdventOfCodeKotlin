package AdventOfCodeKotlin.util

class Graph<T: Node>(val width: Int, val height: Int) : HashMap<Pair<Int, Int>, T>() {
    companion object {

        fun <T: AdventOfCodeKotlin.util.Node> buildGraph(
            input: List<List<Char>>,
            initialize: (Int, Int, Char) -> T,
            includePredicate: (Char) -> Boolean = { true },
            callbacks: Map<Char, (Char, T) -> Unit> = mapOf()
        ): Graph<T> {
            val allNodes = Graph<T>(input[0].size, input.size)

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

open class Node(val x: Int, val y: Int, val label: Char) : Adjacent() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Node

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
        return "Node(x=$x, y=$y, label=$label)"
    }
}

open class Node3d(x: Int, y: Int, val z: Int, label: Char) : Node(x, y, label) {

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
        return "Node3d(x=$x, y=$y, z=$z, label=$label)"
    }
}