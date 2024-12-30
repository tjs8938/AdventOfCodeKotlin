package AdventOfCodeKotlin.puzzles.AoC2024

import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner
import AdventOfCodeKotlin.puzzles.AoC2022.plus
import AdventOfCodeKotlin.util.Node
import AdventOfCodeKotlin.util.Graph.Companion.buildGraph

class Day04 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {
            val input = puzzle.getAsString().map { it.toList() }
            val graph = buildGraph(input, ::Node)

            fun findXMAS(start: Pair<Int, Int>, direction: Pair<Int, Int>): Int {
                var current = start
                var count = 0
                listOf('M', 'A', 'S').forEachIndexed { index, c ->
                    current = current + direction
                    if (graph.containsKey(current) && graph[current]!!.label == c) {
                        count++
                    }
                }
                return count / 3
            }

            return graph.filterValues { it.label == 'X' }.flatMap { (start, _) ->
                listOf(
                    findXMAS(start, 0 to 1),
                    findXMAS(start, 1 to 0),
                    findXMAS(start, 1 to 1),
                    findXMAS(start, 1 to -1),
                    findXMAS(start, 0 to -1),
                    findXMAS(start, -1 to -1),
                    findXMAS(start, -1 to 1),
                    findXMAS(start, -1 to 0)
                )
            }.sum().toString()
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            val input = puzzle.getAsString().map { it.toList() }
            val graph = buildGraph(input, ::Node)

            fun findXMAS(start: Pair<Int, Int>): Int {
                var current = start
                val directions = listOf(-1 to -1, -1 to 1, 1 to 1, 1 to -1)
                val corners = directions.mapNotNull { dir ->
                    val point = current + dir
                    if (graph.containsKey(point)) {
                        graph[point]!!.label
                    } else {
                        null
                    }
                }.joinToString("")
                return if (corners in listOf("MMSS", "SMMS", "SSMM", "MSSM")) {
                    1
                } else {
                    0
                }
            }

            return graph.filterValues { it.label == 'A' }.map { (start, _) ->
                    findXMAS(start)
            }.sum().toString()
        }
    }
}



fun main() {
    Runner.solve(2024, 4, part1 = Day04::part1)

    Runner.solve(2024, 4, part2 = Day04::part2)
}