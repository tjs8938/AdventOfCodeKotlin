package AdventOfCodeKotlin.puzzles.AoC2023

import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner


class Day22 {
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
    Runner.solve(2023, 22, part1 = Day22::part1)
    Runner.solve(2023, 22, part2 = Day22::part2)
}