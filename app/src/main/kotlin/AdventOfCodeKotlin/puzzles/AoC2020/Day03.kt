package AdventOfCodeKotlin.puzzles.AoC2020

import AdventOfCodeKotlin.framework.ExamplePuzzle
import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner

class Day03 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider) : String {
            val input = puzzle
                .getAsString()

            return howManyTrees(input, Pair(3, 1)).toString()
        }

        fun part2(puzzle: PuzzleInputProvider) : String {
            val input = puzzle
                .getAsString()

            val slopes: List<Pair<Int, Int>> = listOf(1 to 1, 3 to 1, 5 to 1, 7 to 1, 1 to 2)

            return slopes.map { howManyTrees(input, it).toBigInteger() }
                .reduce{a, b -> a * b}.toString()
        }

        fun howManyTrees(hill: List<String>, slope: Pair<Int, Int>) : Int {
            val width = hill[0].length
            val (dx, dy) = slope
            return hill.indices.count { y ->  y % dy == 0 && hill[y][y * dx / dy % width] == '#' }
        }
    }
}


fun main() {
    println("Example 1: ${Day03.part2(ExamplePuzzle("""
        ..##.......
        #...#...#..
        .#....#..#.
        ..#.#...#.#
        .#...##..#.
        ..#.##.....
        .#.#.#....#
        .#........#
        #.##...#...
        #...##....#
        .#..#...#.#
    """.trimIndent()))}")
    Runner.solve(2020, 3, Day03::part1, Day03::part2)
}