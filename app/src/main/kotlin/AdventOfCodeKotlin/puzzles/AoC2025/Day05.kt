package AdventOfCodeKotlin.puzzles.AoC2025

import AdventOfCodeKotlin.framework.ExamplePuzzle
import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner

class Day05 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {
            val (rangeStr, idStr) = puzzle.get().split("\n\n")
            val ranges = rangeStr.lines().map {
                val (start, end) = it.split("-").map { numStr -> numStr.toLong() }
                start..end
            }
            val ids = idStr.lines().map { it.trim().toLong() }

            return ids.count { ranges.any { range -> it in range } }.toString()
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            val (rangeStr, _) = puzzle.get().split("\n\n")
            val ranges = rangeStr.lines().map {
                val (start, end) = it.split("-").map { numStr -> numStr.toLong() }
                start..end
            }
                .sortedWith(compareBy { it.first })
                .toMutableList()

            var index = ranges.size - 1
            while (index > 0) {
                val current = ranges[index]
                for (otherIndex in 0 until index) {
                    val other = ranges[otherIndex]
                    if (current.first in other || current.last in other || maxOf(current.last, other.last) + 1 == minOf(current.first, other.first)) {
                        val newRange = minOf(current.first, other.first)..maxOf(current.last, other.last)
                        ranges[otherIndex] = newRange
                        ranges.remove(current)
                        break
                    }
                }
                index--
            }
            val result = ranges.sumOf { it.last - it.first + 1 }
            return result.toString()
        }
    }
}

fun main() {
    Runner.solve(2025, 5, part1 = Day05::part1)

    val example = ExamplePuzzle(
        """
        3-5
        10-14
        16-20
        12-18
    """.trimIndent()
    )
    Day05.part2(example)
        .let {
            println(it)
            it
        }.takeIf { it == "14" }
        ?: error("Part2 - Example failed")

    Runner.solve(2025, 5, part2 = Day05::part2)
}

