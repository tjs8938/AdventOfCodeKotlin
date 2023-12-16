package AdventOfCodeKotlin.puzzles.AoC2023

import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner


class Day09 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {
            return puzzle.getAsString().map { it.split(" ").map { l -> l.toLong() } }
                .map { start ->
                    var diffs = start
                    var next = 0L
                    while (diffs.any { it != 0L }) {
                        next += diffs.last()
                        diffs = diffs.subList(0, diffs.size - 1).zip(diffs.subList(1, diffs.size)).map { it.second - it.first }
                    }
                    next
                }
                .sum().toString()
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            return puzzle.getAsString().map { it.split(" ").map { l -> l.toLong() } }
                .map { start ->
                    var diffs = start
                    val first = mutableListOf(diffs.first())
                    while (diffs.any { it != 0L }) {
                        diffs = diffs.subList(0, diffs.size - 1).zip(diffs.subList(1, diffs.size)).map { it.second - it.first }
                        first.add(diffs.first())
                    }
                    var newFirst = 0L
                    first.reversed().forEach { newFirst = it - newFirst }
                    newFirst
                }
                .sum().toString()
        }
    }
}


fun main() {
//    Runner.solve(2023, 9, part1 = Day09::part1)
    Runner.solve(2023, 9, part2 = Day09::part2)
}