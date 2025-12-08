package AdventOfCodeKotlin.puzzles.AoC2025

import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner
import AdventOfCodeKotlin.util.Graph.Companion.buildGraph
import AdventOfCodeKotlin.util.Node

class Day04 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {
            val graph = buildGraph(puzzle.getAsGrid(), ::Node,
                includePredicate = { it == '@' },
                neighborPredicate = { _, _ -> true})
            return graph.values.count { it.neighbors.size < 4 }.toString()
        }
        fun part2(puzzle: PuzzleInputProvider): String {

            val graph = buildGraph(
                puzzle.getAsGrid(), ::Node,
                includePredicate = { it == '@' },
                neighborPredicate = { _, _ -> true })

            var count = 0
            while (true) {
                val canBeMoved = graph.filter { it.value.neighbors.size < 4 }
                if (canBeMoved.isEmpty()) break

                count += canBeMoved.size
                canBeMoved.forEach {
                    val removed = graph.remove(it.key)
                    removed?.neighbors?.forEach { neighbor ->
                        neighbor.neighbors.remove(removed)
                    }
                }
            }
            return count.toString()
        }
    }
}

fun main() {
    Runner.solve(2025, 4, part1 = Day04::part1)
    Runner.solve(2025, 4, part2 = Day04::part2)
}

