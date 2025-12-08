package AdventOfCodeKotlin.puzzles.AoC2024

import AdventOfCodeKotlin.oldframework.ExamplePuzzle
import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner
import AdventOfCodeKotlin.util.MemoizedFunction.Companion.memoize

class Day11 {
    companion object {

        fun part1(puzzle: PuzzleInputProvider): Any {
            return commonDay11(puzzle, 25)
        }

        private fun commonDay11(puzzle: PuzzleInputProvider, totalBlinks: Int): Long {
            val stones = puzzle.get().split(" ").map { it.toLong() }

            val countStones = memoize<Pair<Long, Int>, Long> { stone ->
                val (n, blinks) = stone
                if (blinks == 0) {
                    1L
                } else {
                    if (n == 0L) {
                        this(1L to blinks - 1)
                    } else if (n.toString().length % 2 == 0) {
                        val splits = n.toString().chunked(n.toString().length / 2)
                        this(splits[0].toLong() to blinks - 1) + this(splits[1].toLong() to blinks - 1)
                    } else {
                        this(n * 2024 to blinks - 1)
                    }
                }
            }

            return stones.sumOf { countStones(it to totalBlinks) }
        }

        fun part2(puzzle: PuzzleInputProvider): Any {
            return commonDay11(puzzle, 75)
        }
    }
}

fun main() {

    val ex1 = ExamplePuzzle("""125 17""")
    Day11.part1(ex1).let {
        println(it)
        assert(it == 55312)
    }
    Runner.solve(2024, 11, part1 = Day11::part1)

    Runner.solve(2024, 11, part2 = Day11::part2)
}