package AdventOfCodeKotlin.puzzles.AoC2023

import AdventOfCodeKotlin.oldframework.ExamplePuzzle
import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner


class Day01 {
    companion object {

        val numeralStrings =
            listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

        fun part1(puzzle: PuzzleInputProvider): String {
            return puzzle.getAsString().map {
                val regex = Regex("""([0-9])""")
                val results = regex.findAll(it).toList()
                results.first().value + results.last().value
            }.map { it.toInt() }.sum().toString()
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            return puzzle.getAsString().map {
                val orPattern = numeralStrings.joinToString("|")
                val orPatternReversed = orPattern.reversed()
                val regexStart = Regex("""([0-9]|$orPattern)""")
                val regexEnd = Regex("""([0-9]|$orPatternReversed)""")
                val answer = toDigit(regexStart, it) + toDigit(regexEnd, it.reversed())
                answer
            }.map { it.toInt() }.sum().toString()
        }

        private fun toDigit(regex: Regex, s: String): String {
            return regex.findAll(s)
                .first()
                .destructured
                .let { (value) ->
                    if (value.length == 1) {
                        value
                    } else {
                        if (numeralStrings.indexOf(value) >= 0) {
                            (numeralStrings.indexOf(value) + 1).toString()
                        } else {
                            (numeralStrings.indexOf(value.reversed()) + 1).toString()
                        }
                    }
                }
        }
    }
}


fun main() {
//    Runner.solve(2023, 1, part1 = Day01::part1)
    val example = ExamplePuzzle(
        """
        two1nine
        eightwothree
        abcone2threexyz
        xtwone3four
        4nineeightseven2
        zoneight234
        7pqrstsixteen
    """.trimIndent()
    )
    assert(Day01.part2(example) == "281")
    val ex2 = ExamplePuzzle("plckgsixeight6eight95bnrfonetwonej")
    assert(Day01.part2(ex2) == "61")
    Runner.solve(2023, 1, part2 = Day01::part2)
}