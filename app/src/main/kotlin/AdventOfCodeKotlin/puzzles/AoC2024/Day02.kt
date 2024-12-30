package AdventOfCodeKotlin.puzzles.AoC2024

import AdventOfCodeKotlin.framework.ExamplePuzzle
import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner
import kotlin.math.abs


class Day02 {
    companion object {

        fun part1(puzzle: PuzzleInputProvider): String {
            val result = puzzle.getAsString().count { line ->
                val levels = line.split(" ").map { it.toInt() }
                testLevels(levels)
            }

            return result.toString()
        }

        private fun testLevels(levels: List<Int>) =
            levels.zipWithNext().all { (a, b) -> a - b in listOf(1, 2, 3) } ||
                    levels.zipWithNext().all { (a, b) -> b - a in listOf(1, 2, 3) }

        fun part2(puzzle: PuzzleInputProvider): String {
            val result = puzzle.getAsString().count { line ->
                val levels = line.split(" ").map { it.toInt() }
                if (testLevels(levels)) {
                    return@count true
                } else {
                    return@count levels.indices.any { i ->
                        val newLevels = levels.toMutableList()
                        newLevels.removeAt(i)
                        testLevels(newLevels)
                    }
                }
            }

            return result.toString()
        }

    }
}


fun main() {
    Runner.solve(2024, 2, part1 = Day02::part1)

    val example = ExamplePuzzle("""
        7 6 4 2 1
        1 2 7 8 9
        9 7 6 2 1
        1 3 2 4 5
        8 6 4 4 1
        1 3 6 7 9
    """.trimIndent())
    assert(Day02.part2(example) == "4")
    Runner.solve(2024, 2, part2 = Day02::part2)
}