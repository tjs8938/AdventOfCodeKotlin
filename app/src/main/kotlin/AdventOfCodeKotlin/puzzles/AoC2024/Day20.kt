package AdventOfCodeKotlin.puzzles.AoC2024

import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner
import AdventOfCodeKotlin.util.Adjacent.Companion.buildRouteTable
import AdventOfCodeKotlin.util.Direction
import AdventOfCodeKotlin.util.Graph.Companion.buildGraph
import AdventOfCodeKotlin.util.Node
import kotlin.math.abs

class Day20 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): Any {

            val input = puzzle.getAsGrid()

            var lateStart: Node<Char>? = null
            var lateEnd: Node<Char>? = null
            val graph = buildGraph(
                input, ::Node, { it != '#' },
                mapOf('S' to { _, n -> lateStart = n }, 'E' to { _, n -> lateEnd = n })
            )

            val start = lateStart!!
            val end = lateEnd!!

            val routes = buildRouteTable(listOf(start, end))

            val target = routes[start]!![end]!! - 100

            val candidates = graph.keys.flatMap {
                val goodPairs = mutableListOf<List<Pair<Int, Int>>>()
                val nextEast = Direction.EAST + it
                val secondEast = Direction.EAST + nextEast
                if (nextEast !in graph && secondEast in graph) {
                    goodPairs.add(listOf(it, secondEast))
                }

                val nextSouth = Direction.SOUTH + it
                val secondSouth = Direction.SOUTH + nextSouth
                if (nextSouth !in graph && secondSouth in graph) {
                    goodPairs.add(listOf(it, secondSouth))
                }

                goodPairs
            }

            return candidates.count { l ->
                var closerToStart = graph[l.minBy { routes[start]!![graph[it]!!]!! }]!!
                var fartherFromStart = graph[l.maxBy { routes[start]!![graph[it]!!]!! }]!!

                (routes[start]!![closerToStart]!! + routes[end]!![fartherFromStart]!! + 2) <= target
            }
        }

        fun part2(puzzle: PuzzleInputProvider): Any {
            return common(puzzle, 20)
        }
        fun common(puzzle: PuzzleInputProvider, cheatSize: Int): Any {
            val input = puzzle.getAsGrid()

            var lateStart: Node<Char>? = null
            var lateEnd: Node<Char>? = null
            val graph = buildGraph(
                input, ::Node, { it != '#' },
                mapOf('S' to { _, n -> lateStart = n }, 'E' to { _, n -> lateEnd = n })
            )

            val start = lateStart!!
            val end = lateEnd!!

            val routes = buildRouteTable(listOf(start, end))

            val target = routes[start]!![end]!! - 100

            val cheats: MutableList<List<Pair<Int, Int>>> = mutableListOf()

            graph.keys.forEach { a ->
                graph.keys.forEach { b ->
                    val nodeA = graph[a]!!
                    val nodeB = graph[b]!!
                    if (routes[start]!![nodeA]!! < routes[start]!![nodeB]!!) {
                        val dist = abs(a.first - b.first) + abs(a.second - b.second)
                        if (dist <= cheatSize) {
                            if (routes[start]!![nodeA]!! + routes[end]!![nodeB]!! + dist <= target) {
                                cheats.add(listOf(a, b))
                            }
                        }
                    }
                }
            }

            return cheats.size
        }
    }
}


fun main() {


    Runner.solve(2024, 20, part1 = Day20::part1)
    Runner.solve(2024, 20, part1 = { Day20.common(it, 2) })


    Runner.solve(2024, 20, part2 = Day20::part2)
}