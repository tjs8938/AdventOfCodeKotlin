package AdventOfCodeKotlin.puzzles.AoC2025

import AdventOfCodeKotlin.framework.ExamplePuzzle
import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner
import kotlin.math.abs


class Day01 {
    companion object {

        fun part1(puzzle: PuzzleInputProvider): String {
            var position = 50
            var zeroCount = 0
            puzzle.getAsString().forEach {
                val direction = it[0]
                val distance = it.substring(1).toInt()
                position += if (direction == 'L') {
                    -distance
                } else {
                    distance
                }
                position = position.mod(100)
                if (position == 0) {
                    zeroCount++
                }
            }
            return zeroCount.toString()
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            var position = 50
            var zeroCount = 0
            puzzle.getAsString().forEach {
                val direction = it[0]
                val distance = it.substring(1).toInt()
                val lastPosition = position
                position += if (direction == 'L') {
                    -distance
                } else {
                    distance
                }
                if ((lastPosition > 0 && position < 0) || (lastPosition < 0 && position > 0) || (position == 0)) {
                    zeroCount++
                }
                zeroCount += abs(position / 100)
                position = position.mod(100)

            }
            return zeroCount.toString()
        }

    }
}


fun main() {
    assert(Day01.part1(ExamplePuzzle("""
        L68
        L30
        R48
        L5
        R60
        L55
        L1
        L99
        R14
        L82
    """.trimIndent())) == "3")

    Runner.solve(2025, 1, part1 = Day01::part1)

//    assert(Day01.part2(ExamplePuzzle("""
//        L68
//        L30
//        R48
//        L5
//        R60
//        L55
//        L1
//        L99
//        R14
//        L82
//    """.trimIndent())) == "6")
    Runner.solve(2025, 1, part2 = Day01::part2)
}