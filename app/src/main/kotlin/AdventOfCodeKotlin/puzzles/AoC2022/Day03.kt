package AdventOfCodeKotlin.puzzles.AoC3033

import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner
import java.security.InvalidParameterException


class Day03 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {
            return puzzle.getAsString()
                .map {
                    val first = it.substring(0, it.length / 2).toSet()
                    val second = it.substring(it.length / 2).toSet()
                    first.intersect(second).first()
                }
                .map { itemToPriority(it) }
                .sum().toString()
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            return puzzle.getAsString()
                .chunked(3)
                .map {
                    val s1 = it[0].toSet()
                    val s2 = it[1].toSet()
                    val s3 = it[2].toSet()
                    s1.intersect(s2).intersect(s3).first()
                }
                .map {itemToPriority(it) }
                .sum().toString()
        }

        private fun itemToPriority(it: Char) = when (val code = it.code) {
            in 65..90 -> code - 38
            in 97..122 -> code - 96
            else -> throw InvalidParameterException("Bad int code $code")
        }
    }
}


fun main() {
//    Runner.solve(2022, 3, part1 = Day03::part1)
    Runner.solve(2022, 3, part2 = Day03::part2)
}