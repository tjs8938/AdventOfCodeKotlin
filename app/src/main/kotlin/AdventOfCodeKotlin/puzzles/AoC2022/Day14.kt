package AdventOfCodeKotlin.puzzles.AoC2022

import AdventOfCodeKotlin.framework.ExamplePuzzle
import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner
import kotlin.math.abs


class Day14 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {

            val filledSpaces = parseInput(puzzle)

            val bottomRow = filledSpaces.map { it.second }.max()

            var sandCount = 0
            while (true) {
                val sand = moveSand(bottomRow, filledSpaces) { current, bottom -> current < bottom }

                if (sand.second == bottomRow) {
                    // Sand has fallen even with the lowest rock. It will now fall forever, and shouldn't be counted
                    break
                } else {
                    filledSpaces.add(sand)
                    sandCount++
                }


            }
            return sandCount.toString()
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            val filledSpaces = parseInput(puzzle)

            val bottomRow = filledSpaces.map { it.second }.max()

            var sandCount = 0
            while (true) {

                val sand = moveSand(bottomRow, filledSpaces) { current, bottom -> current <= bottom }

                filledSpaces.add(sand)
                sandCount++
                if (sand == (500 to 0)) {
                    // Sand cannot fall from the opening
                    break
                }
            }
            return sandCount.toString()
        }

        private fun moveSand(
            bottomRow: Int,
            filledSpaces: MutableSet<Pair<Int, Int>>,
            comparison: (Int, Int) -> Boolean
        ): Pair<Int, Int> {
            var sand = 500 to 0
            val moves = listOf(0 to 1, -1 to 1, 1 to 1)

            var settled = false
            while (!settled && comparison(sand.second, bottomRow)) {
                settled = true
                for (it in moves) {
                    if (!filledSpaces.contains(sand + it)) {
                        sand += it
                        settled = false
                        break
                    }
                }
            }
            return sand
        }

        private fun parseInput(puzzle: PuzzleInputProvider): MutableSet<Pair<Int, Int>> {
            val filledSpaces = mutableSetOf<Pair<Int, Int>>()

            puzzle.getAsString()
                .map {
                    it.split(" -> ").map { it.split(",").map { it.toInt() } }
                        .map { it.let { (x, y) -> x to y } }
                }
                .forEach { path ->
                    var current = path[0]
                    filledSpaces.add(current)
                    (1 until path.size).forEach {
                        val end = path[it]
                        val move = if (current.first != end.first) {
                            val diff = end.first - current.first
                            (diff / abs(diff)) to 0
                        } else {
                            val diff = end.second - current.second
                            0 to (diff / abs(diff))
                        }
                        while (current != end) {
                            current += move
                            filledSpaces.add(current)
                        }
                    }
                }
            return filledSpaces
        }
    }
}

operator fun Pair<Int, Int>.plus(move: Pair<Int, Int>): Pair<Int, Int> {
    return (first + move.first) to (second + move.second)
}


fun main() {

    val example = ExamplePuzzle(
        "498,4 -> 498,6 -> 496,6\n" +
            "503,4 -> 502,4 -> 502,9 -> 494,9"
    )
    println(Day14.part2(example))
    Runner.solve(2022, 14, part1 = Day14::part1)
    Runner.solve(2022, 14, part2 = Day14::part2)
}

