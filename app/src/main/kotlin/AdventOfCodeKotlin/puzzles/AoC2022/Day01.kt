package AdventOfCodeKotlin.puzzles.AoC2022

import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner


class Day01 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {
            return puzzle.get().split("\r\n\r\n", "\n\n")
                .map { it.split("\n").map { it.toInt() } }
                .maxBy { it.sum() }
                .sum()
                .toString()
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            return puzzle.get().split("\r\n\r\n", "\n\n")
                .map { it.split("\n").map { it.toInt() } }
                .map { it.sum() }
                .sortedDescending()
                .slice(0..2)
                .sum()
                .toString()
        }
    }
}


fun main() {
    Runner.solve(2022, 1, part1 = Day01::part1)
    Runner.solve(2022, 1, part2 = Day01::part2)
}