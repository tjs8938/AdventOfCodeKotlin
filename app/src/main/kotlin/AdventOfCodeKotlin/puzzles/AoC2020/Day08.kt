package AdventOfCodeKotlin.puzzles.AoC2020

import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner


class Day08 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {
            return puzzle.get()
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            return puzzle.get()
        }
    }
}


fun main() {
    Runner.solve(2020, 8, part1 = Day08::part1)
    Runner.solve(2020, 8, part2 = Day08::part2)
}