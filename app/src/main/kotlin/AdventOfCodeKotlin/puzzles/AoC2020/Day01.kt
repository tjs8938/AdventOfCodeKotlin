package AdventOfCodeKotlin.puzzles.AoC2020

import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner


fun List<Int>.findPairOfSum(sum: Int): Pair<Int, Int>? {
    // Map: sum - x -> x
    val complements = associateBy { sum - it }
    return firstNotNullOfOrNull { number ->
        return complements[number]?.let { number to it }
    }
}

fun List<Int>.findTripleOfSum(): Triple<Int, Int, Int>? =
    firstNotNullOfOrNull { x ->
        findPairOfSum(2020 - x)?.let { pair ->
            Triple(x, pair.first, pair.second)
        }
    }


class Day01 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider) : String {
            val numbers = puzzle.getAsInt()
            val pair = numbers.findPairOfSum(2020)
            return (pair?.let { (a, b) -> a * b }).toString()
        }

        fun part2(puzzle: PuzzleInputProvider) : String {
            val numbers = puzzle.getAsInt()
            val triple = numbers.findTripleOfSum()
            return (triple?.let { (x, y, z) -> x * y * z }).toString()
        }
    }
}


fun main() {
    Runner.solve(2020, 1, Day01::part1, Day01::part2)
}