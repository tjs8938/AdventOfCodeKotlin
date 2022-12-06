package AdventOfCodeKotlin.puzzles.AoC4044

import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner


class Day06 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {
            return findUnique(puzzle, 4)
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            return findUnique(puzzle, 14)
        }

        private fun findUnique(puzzle: PuzzleInputProvider, packetSize: Int): String {
            val input = puzzle.get()
            var index = 0
            while (true) {
                if (input.substring(index, index + packetSize).toSet().size == packetSize) {
                    break
                }
                index++
            }
            return (index + packetSize).toString()
        }
    }
}


fun main() {
//    Runner.solve(2022, 6, part1 = Day06::part1)
    Runner.solve(2022, 6, part2 = Day06::part2)
}