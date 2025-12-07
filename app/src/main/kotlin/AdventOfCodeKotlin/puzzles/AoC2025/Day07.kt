package AdventOfCodeKotlin.puzzles.AoC2025

import AdventOfCodeKotlin.framework.ExamplePuzzle
import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner
import AdventOfCodeKotlin.util.Graph.Companion.buildGraph
import AdventOfCodeKotlin.util.Node

class Day07 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {

            var sLoc = 0
            val splitters = mutableMapOf<Int, MutableList<Int>>()

            val graph = buildGraph(puzzle.getAsGrid(), ::Node,
                callbacks = mapOf(
                    'S' to { _, node -> sLoc = node.x },
                    '^' to { _, node -> splitters.getOrPut(node.y, ::mutableListOf).add(node.x) }
                ))

            var splitCount = 0
            var lasers = setOf(sLoc)
            (0 until graph.height).forEach { row ->
                lasers = lasers.flatMap { laser ->
                    if (row in splitters && laser in splitters[row]!!) {
                        splitCount++
                        listOf(laser - 1, laser + 1)
                    } else {
                        listOf(laser)
                    }
                }.toSet()
            }
            return splitCount.toString()
        }
        fun part2(puzzle: PuzzleInputProvider): String {
            var sLoc = 0
            val splitters = mutableMapOf<Int, MutableList<Int>>()

            val graph = buildGraph(puzzle.getAsGrid(), ::Node,
                callbacks = mapOf(
                    'S' to { _, node -> sLoc = node.x },
                    '^' to { _, node -> splitters.getOrPut(node.y, ::mutableListOf).add(node.x) }
                ))

            var lasers = mapOf(sLoc to 1L)
            (0 until graph.height).forEach { row ->
                val temp = lasers.flatMap { (laser, count) ->
                    if (row in splitters && laser in splitters[row]!!) {
                        listOf(laser - 1 to count, laser + 1 to count)
                    } else {
                        listOf(laser to count)
                    }
                }
                lasers = temp.groupBy({ it.first }, { it.second }).mapValues { it.value.sum() }
            }
            return lasers.values.sum().toString()
        }
    }
}

fun main() {
    Runner.solve(2025, 7, part1 = Day07::part1)

    val example = ExamplePuzzle(
        """
            .......S.......
            ...............
            .......^.......
            ...............
            ......^.^......
            ...............
            .....^.^.^.....
            ...............
            ....^.^...^....
            ...............
            ...^.^...^.^...
            ...............
            ..^...^.....^..
            ...............
            .^.^.^.^.^...^.
            ...............
        """.trimIndent()
    )
    Day07.part2(example)
        .let {
            println(it)
            it
        }.takeIf { it == "40" }
        ?: error("Part2 - Example failed")

    Runner.solve(2025, 7, part2 = Day07::part2)
}

