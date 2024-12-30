package AdventOfCodeKotlin.util

abstract class Adjacent {

    val neighbors: MutableList<Adjacent> = mutableListOf()

    fun addNeighbor(n: Adjacent) {
        neighbors.add(n)
        n.neighbors.add(this)
    }

    companion object {
        fun buildRouteTable(nodes: List<Adjacent>, condition: (Adjacent, Adjacent)-> Boolean = { a1, a2 -> true}):
                Map<Adjacent, Map<Adjacent, Int>> {
            val routeTable = mutableMapOf<Adjacent, MutableMap<Adjacent, Int>>()
            nodes.forEach { node ->
                routeTable[node] = mutableMapOf()
                routeTable[node]!![node] = 0
                val processed = mutableSetOf(node)
                var toProcess = node.neighbors.filter { n -> condition(node, n) }.toSet()
                var distance = 0
                while (toProcess.isNotEmpty()) {
                    distance++

                    routeTable[node]!!.putAll(toProcess.map { it to distance })
                    processed.addAll(toProcess)
                    toProcess = toProcess.flatMap { start ->
                        start.neighbors.filter { n -> n !in processed && condition(start, n) }
                    }.toSet()
                }
            }
            return routeTable
        }
    }
}