package AdventOfCodeKotlin.puzzles.AoC2024

import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner
import kotlin.math.abs


class Day01 {
    companion object {

        fun part1(puzzle: PuzzleInputProvider): String {
            val left = mutableListOf<Int>()
            val right = mutableListOf<Int>()
            return puzzle.getAsString().map {
                it.split(" ").mapNotNull {
                    if (it.isNotBlank()) {
                        it.trim().toInt()
                    } else {
                        null
                    }
                }
            }.forEach { (l, r) ->
                left.add(l)
                right.add(r)
            }.let { left.sorted().zip(right.sorted()) { l, r -> abs(l - r) }.sum() }.toString()
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            val left = mutableListOf<Int>()
            val right = mutableListOf<Int>()
            puzzle.getAsString().map {
                it.split(" ").mapNotNull {
                    if (it.isNotBlank()) {
                        it.trim().toInt()
                    } else {
                        null
                    }
                }
            }.forEach { (l, r) ->
                left.add(l)
                right.add(r)
            }

            val counts = right.groupingBy { it }.eachCount()
            return left.sumOf { it * (counts[it] ?: 0) }.toString()
        }

    }
}


fun main() {
    Runner.solve(2024, 1, part1 = Day01::part1)

    Runner.solve(2024, 1, part2 = Day01::part2)
}