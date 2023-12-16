package AdventOfCodeKotlin.puzzles.AoC2019

import AdventOfCodeKotlin.framework.ExamplePuzzle
import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner


class Day01 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {
            return puzzle.getAsInt().map { calcFuel(it) }.sum().toString()
        }

        private fun calcFuel(it: Int) = (it / 3) - 2

        private fun calcFuelRecursive(mass: Int): Int {
            var total = 0
            var recent = calcFuel(mass)
            while (recent > 0) {
                total += recent
                recent = calcFuel(recent)
            }
            return total
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            return puzzle.getAsInt().map { calcFuelRecursive(it) }.sum().toString()
        }
    }
}


fun main() {

    val ex1 = ExamplePuzzle("14")
    val ex2 = ExamplePuzzle("1969")
    val ex3 = ExamplePuzzle("100756")

    assert (Day01.part2(ex1) == "2")
    assert (Day01.part2(ex2) == "966")
    assert (Day01.part2(ex3) == "50346")
//    Runner.solve(2019, 1, part1 = Day01::part1)
    Runner.solve(2019, 1, part2 = Day01::part2)
}