package AdventOfCodeKotlin.puzzles.AoC2025

import AdventOfCodeKotlin.framework.ExamplePuzzle
import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner

class Day06 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {
            val input = puzzle.getAsString()
            val operations: List<Pair<(Long, Long) -> Long, Long>> = input.last()
                .split(" ")
                .map { it.trim() }
                .filter { it.isNotEmpty() }
                .map { it[0] }
                .map { if (it == '+') Pair(Long::plus, 0) else Pair(Long::times, 1) }

            val rows = input.dropLast(1)
                .map {
                    it.split(" ")
                        .filter { it.isNotEmpty() }
                        .map {
                            it.trim().toLong()
                        }
                }

            val vertical = (0 until operations.size).map { column -> rows.map { it[column] } }
            return vertical.zip(operations).sumOf { (nums, op) ->
                nums.fold(op.second) { acc, n -> op.first(acc, n) }
            }.toString()
        }

        fun getSpace(index: Int) : Char {
            return ' '
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            val input = puzzle.getAsGrid()
            val verticals = (input[0].size - 1 downTo 0).map { col ->
                input.map { row -> row.getOrElse(col, ::getSpace) }.joinToString("").trim()
            }

            val problemLines = verticals.joinToString("\n").split("\n\n").map { it.split("\n").map { it.trim() }.toMutableList() }
            return problemLines.sumOf { problem ->
                val last = problem.last()
                val operation: Pair<(Long, Long) -> Long, Long> = if (last.last() == '+') Pair(Long::plus, 0) else Pair(Long::times, 1)
                problem[problem.size - 1] = last.dropLast(1).trim()
                val numbers = problem.map { it.trim().toLong() }
                numbers.fold(operation.second) { acc, n -> operation.first(acc, n) }
            }.toString()
        }
    }
}

fun main() {
    Runner.solve(2025, 6, part1 = Day06::part1)

    val example = ExamplePuzzle(
        """
            123 328  51 64 
             45 64  387 23 
              6 98  215 314
            *   +   *   +  
        """.trimIndent()
    )
    Day06.part2(example)
        .let {
            println(it)
            it
        }.takeIf { it == "3263827" }
        ?: error("Part2 - Example failed")

    Runner.solve(2025, 6, part2 = Day06::part2)
}

