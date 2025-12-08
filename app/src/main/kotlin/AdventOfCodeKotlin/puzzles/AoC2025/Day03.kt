package AdventOfCodeKotlin.puzzles.AoC2025

import AdventOfCodeKotlin.oldframework.ExamplePuzzle
import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner

class Day03 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {
            val tests = (99 downTo 0)
                .map { it.toString().padStart(2, '0') }
                .map { testNumber ->
                    Regex("${testNumber[0]}.*${testNumber[1]}")
                }

            val result = puzzle.getAsString()
                .map { line -> 99 - tests.indexOfFirst { it.containsMatchIn(line) } }
            return result.sum().toString()
        }

        fun highestWithNLeft(input: String, start: Int, digitCount: Int): String {
            if (digitCount == 0) return ""

            val best = input.substring(start, input.length - digitCount + 1)
                .mapIndexed { index, c -> index to c }
                .minWith(compareBy<Pair<Int, Char>> { it.second }.reversed().thenBy { it.first })

            val result = best.let {
                it.second + highestWithNLeft(input, start + it.first + 1, digitCount - 1)
            }
            return result
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            return puzzle.getAsString().sumOf { line -> highestWithNLeft(line, 0, 12).toLong() }.toString()
        }
    }
}

fun main() {
    val example = ExamplePuzzle(
        """
        987654321111111
        811111111111119
        234234234234278
        818181911112111
    """.trimIndent()
    )
    Day03.part1(example)
        .let {
            println("Part 1: $it")
            it
        }
        .takeIf { it == "357" }
        ?: error("Part 1 example failed")

    Runner.solve(2025, 3, part1 = Day03::part1)

    Day03.part2(example)
        .let {
            println("Part 2: $it")
            it
        }
        .takeIf { it == "3121910778619" }
        ?: error("Part 2 example failed")

    Runner.solve(2025, 3, part2 = Day03::part2)
}

