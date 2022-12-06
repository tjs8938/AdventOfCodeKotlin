package AdventOfCodeKotlin.puzzles.AoC2020

import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner

class Day05 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider) : String {
            val input = puzzle.getAsString()
            return input.map { it.toSeatID() }.max().toString()
        }

        fun part2(puzzle: PuzzleInputProvider) : String {
            val seats = puzzle.getAsString().map { it.toSeatID() }.sorted()

            for ((index, seatId) in seats.withIndex()) {
                if (seats.get(index + 1) == seatId + 2) {
                    return (seatId + 1).toString()
                }
            }
            return ""
        }

        fun String.toSeatID(): Int = this
            .replace('B', '1').replace('F', '0')
            .replace('R', '1').replace('L', '0')
            .toInt(radix = 2)
    }
}


fun main() {

    Runner.solve(2020, 5, Day05::part1, Day05::part2)
}