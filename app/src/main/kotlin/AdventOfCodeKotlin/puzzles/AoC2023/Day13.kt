package AdventOfCodeKotlin.puzzles.AoC2023

import AdventOfCodeKotlin.oldframework.ExamplePuzzle
import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner


class Day13 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {
            return sumOfReflectionPoints(puzzle) { pattern -> part1ReflectionPoint(pattern) }
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            return sumOfReflectionPoints(puzzle) { pattern -> part2ReflectionPoint(pattern) }
        }

        private fun sumOfReflectionPoints(
            puzzle: PuzzleInputProvider,
            reflectionPoint: (List<String>) -> List<Int>
        ) =
            puzzle.get().split("\r\n\r\n", "\n\n")
                .map { it.split("\r\n", "\n") }
                .sumOf { pattern ->
                    val horizontalRefection = reflectionPoint(pattern)

                    if (horizontalRefection.isNotEmpty()) {
                        horizontalRefection.first() * 100
                    } else {
                        val flipped = pattern[0].indices.map { column ->
                            pattern.map { it[column] }
                                .joinToString("")
                        }

                        reflectionPoint(flipped).first()
                    }
                }
                .toString()

        private fun part1ReflectionPoint(pattern: List<String>) = (1 until pattern.size).filter {
            val pairs = pattern.subList(0, it).reversed().zip(pattern.subList(it, pattern.size))
            pairs.isNotEmpty() && pairs.all { pair -> pair.first == pair.second }
        }

       private fun part2ReflectionPoint(pattern: List<String>) = (1 until pattern.size).filter {
            val pairs = pattern.subList(0, it).reversed().zip(pattern.subList(it, pattern.size))

            val diffs: Int = pairs.sumOf { pair ->
                pair.first.zip(pair.second)
                    .count { p -> p.first != p.second }
            }

            pairs.isNotEmpty() && diffs == 1
        }
    }
}


fun main() {

    val ex1 = ExamplePuzzle("""
        #.##..##.
        ..#.##.#.
        ##......#
        ##......#
        ..#.##.#.
        ..##..##.
        #.#.##.#.

        #...##..#
        #....#..#
        ..##..###
        #####.##.
        #####.##.
        ..##..###
        #....#..#
    """.trimIndent())

    println(Day13.part1(ex1))
    assert(Day13.part1(ex1) == "405")
    Runner.solve(2023, 13, part1 = Day13::part1)

    println(Day13.part2(ex1))
    assert(Day13.part2(ex1) == "400")
    Runner.solve(2023, 13, part2 = Day13::part2)
}