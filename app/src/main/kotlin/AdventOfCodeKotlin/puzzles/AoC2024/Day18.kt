package AdventOfCodeKotlin.puzzles.AoC2024

import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner
import AdventOfCodeKotlin.util.Adjacent.Companion.buildRouteTable
import AdventOfCodeKotlin.util.Graph.Companion.buildGraph
import AdventOfCodeKotlin.util.Node

class Day18 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider, size: Int): Any {

            val asChars = MutableList(size) { MutableList(size) { '.' } }
            val input = puzzle.get().lines().map { it.split(",").map { it.trim().toInt() } }.map { it[0] to it[1] }
            input.subList(0, 1024).forEach { (x, y) -> asChars[y][x] = '#' }
            val graph = buildGraph(asChars, ::Node, { it == '.' })
            val routes = buildRouteTable(listOf((graph[0 to 0]!!)))
            return routes[graph[0 to 0]!!]!![graph[size - 1 to size - 1]!!]!!
        }

        fun part2(puzzle: PuzzleInputProvider, size: Int ): Any {

            val asChars = MutableList(size) { MutableList(size) { '.' } }
            val input = puzzle.get().lines().map { it.split(",").map { it.trim().toInt() } }.map { it[0] to it[1] }
            input.subList(0, 1024).forEach { (x, y) -> asChars[y][x] = '#' }
            var knownGood = 1024
            var knownBad = input.size
            var distance: Int? = 0

            while (knownGood + 1 < knownBad && distance != null) {
                knownGood++
                input[knownGood].let { (x, y) -> asChars[y][x] = '#' }

                val graph = buildGraph(asChars, ::Node, { it == '.' })
                val start = graph[0 to 0]!!
                val end = graph[size - 1 to size - 1]!!
                val routes = buildRouteTable(listOf(start))

                distance = routes[start]!![end]
            }
            return input[knownGood].let { (x, y) -> "$x,$y" }
        }
    }
}

fun main() {
//    Runner.solve(2024, 18, part1 = { Day18.part1(it, 71) })
    Runner.solve(2024, 18, part2 = { Day18.part2(it, 71) })
}