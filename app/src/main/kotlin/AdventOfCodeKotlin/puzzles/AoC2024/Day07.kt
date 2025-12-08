package AdventOfCodeKotlin.puzzles.AoC2024

import AdventOfCodeKotlin.oldframework.ExamplePuzzle
import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner

class Day07 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): Any {
            return countValid(puzzle, listOf(Long::plus, Long::times))
        }
        fun countValid(puzzle: PuzzleInputProvider, operations: List<(Long, Long) -> Long>): Any {
            val input = puzzle.getAsString().map { it.split(": ")
                .let { it[0].toLong() to it[1].split(" ").map { it.toLong() }.toMutableList() } }

            fun calcOperatorOptions(remaining: MutableList<Long>): List<Long> {
                var options = mutableListOf<Long>()
                val first = remaining.removeFirst()
                options.add(first)
                while (remaining.isNotEmpty()) {
                    val next = remaining.removeFirst()
                    options = options.flatMap { option ->
                        operations.map { it(option, next) }
                    }.toMutableList()
                }
                return options
            }

            val matching = input.filter { it.first in calcOperatorOptions(it.second) }
            return matching.sumOf{ it.first }
        }

        fun part2(puzzle: PuzzleInputProvider): Any {
            return countValid(puzzle, listOf(Long::plus, Long::times,
                {a, b -> (a.toString() + b.toString()).toLong()}))
        }
    }
}

fun main() {
    val ex1 = ExamplePuzzle("""
        190: 10 19
        3267: 81 40 27
        83: 17 5
        156: 15 6
        7290: 6 8 6 15
        161011: 16 10 13
        192: 17 8 14
        21037: 9 7 18 13
        292: 11 6 16 20
    """.trimIndent())
    assert(Day07.part1(ex1) == 3749L)
    Runner.solve(2024, 7, part1 = Day07::part1)
    Runner.solve(2024, 7, part2 = Day07::part2)
}