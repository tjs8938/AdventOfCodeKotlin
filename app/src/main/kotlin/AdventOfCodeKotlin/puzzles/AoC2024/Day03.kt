package AdventOfCodeKotlin.puzzles.AoC2024

import AdventOfCodeKotlin.framework.ExamplePuzzle
import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner
import kotlin.math.abs


class Day03 {
    companion object {

        fun part1(puzzle: PuzzleInputProvider): String {
            val input = puzzle.get()

            val matches = Regex("""mul\((\d{1,3}),(\d{1,3})\)""").findAll(input)
                .map { it.destructured.let {( a, b) ->
                    a.toLong() * b.toLong()
                } }
            return matches.sum().toString()
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            val input = puzzle.get()

            val matches = Regex("""mul\((\d{1,3}),(\d{1,3})\)|don't\(\)|do\(\)""").findAll(input)
            var enabled = true
            var sum = 0L
            matches.forEach {
                when(it.value) {
                    "do()" -> enabled = true
                    "don't()" -> enabled = false
                    else -> if (enabled) {
                        val (a, b) = it.destructured
                        sum += a.toLong() * b.toLong()
                    }
                }
            }

            return sum.toString()
        }

    }
}


fun main() {
    Runner.solve(2024, 3, part1 = Day03::part1)

    Runner.solve(2024, 3, part2 = Day03::part2)
}