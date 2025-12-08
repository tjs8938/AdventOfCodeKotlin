package AdventOfCodeKotlin.puzzles.AoC2024

import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner
import AdventOfCodeKotlin.util.MemoizedFunction.Companion.memoize


class Day19 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): Any {
            val (availableInput, patternInput) = puzzle.get().split("\n\n")
            val available = availableInput.split(", ")
            val patterns = patternInput.split("\n")

            val evalPattern = memoize<String, Boolean> { pattern ->
                if (pattern.isEmpty()) {
                    return@memoize true
                }

                val options = available.filter { pattern.startsWith(it) }
                if (options.isEmpty()) {
                    return@memoize false
                }

                return@memoize options.any { option ->
                    this(pattern.removePrefix(option))
                }
            }
            return patterns.count { evalPattern(it) }
        }

        fun part2(puzzle: PuzzleInputProvider): Any {
            val (availableInput, patternInput) = puzzle.get().split("\n\n")
            val available = availableInput.split(", ")
            val patterns = patternInput.split("\n")

            val evalPattern = memoize<String, Long> { pattern ->
                if (pattern.isEmpty()) {
                    return@memoize 1L
                }

                val options = available.filter { pattern.startsWith(it) }
                if (options.isEmpty()) {
                    return@memoize 0L
                }

                return@memoize options.sumOf { option ->
                    this(pattern.removePrefix(option))
                }
            }
            return patterns.sumOf { evalPattern(it) }
        }
    }
}


fun main() {


//    Runner.solve(2024, 19, part1 = Day19::part1)


    Runner.solve(2024, 19, part2 = Day19::part2)
}