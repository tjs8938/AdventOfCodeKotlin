package AdventOfCodeKotlin.puzzles.AoC2024

import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner


class Day25 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): Any {

            val inputs = puzzle.get().split("\n\n").map { it.split("\n") }

            val keys = mutableListOf<List<Int>>()
            val locks = mutableListOf<List<Int>>()

            inputs.forEach {
                if (it[0].all { it == '#' }) {
                    val lockHeights = it[0].indices.map { i -> it.map { it[i] }.count { it == '.' } }
                    locks.add(lockHeights)
                } else {
                    val keyHeights = it[0].indices.map { i -> it.map { it[i] }.count { it == '#' } }
                    keys.add(keyHeights)
                }
            }

            var possible = 0
            locks.forEach { lock ->
                keys.forEach { key ->
                    val tumblers = lock.zip(key)
                    if (tumblers.all { (l, k) -> l >= k }) {
                        possible++
                    }
                }
            }


            return possible
        }
    }
}


fun main() {

    Runner.solve(2024, 25, part1 = Day25::part1)
}