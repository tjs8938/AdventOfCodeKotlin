package AdventOfCodeKotlin.puzzles.AoC2024

import AdventOfCodeKotlin.framework.ExamplePuzzle
import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner


class Day22 {
    companion object {
        fun secret(s: Long): Long {
            var result = s xor (s shl 6)
            result = result and 0xffffff
            result = result xor (result shr 5)
            result = result xor (result shl 11)
            return result and 0xffffff
        }
        fun part1(puzzle: PuzzleInputProvider): Any {
            var secrets = puzzle.getAsString().map { it.toLong() }
            repeat(2000) {
                secrets = secrets.map { secret(it) }
            }
            return secrets.sum()
        }

        fun part2(puzzle: PuzzleInputProvider): Any {
            var secrets = puzzle.getAsString().map { it.toLong() }
            val diffs = List(secrets.size) { mutableListOf<Int>() }
            val prices = List(secrets.size) { mutableListOf<Int>() }

                secrets.forEachIndexed { index, s ->
                var lastSecret = s
                repeat(2000) {
                    val newSecret = secret(lastSecret)
                    val p = (newSecret % 10).toInt()
                    val d = p - (lastSecret % 10).toInt()
                    diffs[index].add(d)
                    prices[index].add(p)
                    lastSecret = newSecret
                }
            }

            val diffSequences = diffs.map { it.windowed(4)}.map {
                val l = it.mapIndexed { index, ints -> ints.joinToString() to index + 3 }
                l.groupBy { it.first }.mapValues { it.value.first().second }

            }
            val allDiffs = diffSequences.flatMap { it.keys }.toSet()

            val bestPrices = allDiffs.map { d ->
                diffSequences.mapIndexed { index, monkey ->
                    val i = monkey[d]
                    if (i == null) {
                        0
                    } else {
                        prices[index][i]
                    }
                }.sum()
            }
            return bestPrices.max()
        }
    }
}


fun main() {


//    Runner.solve(2024, 22, part1 = Day22::part1)

    val ex1 = ExamplePuzzle("""
        1
        2
        3
        2024
    """.trimIndent())
    Day22.part2(ex1).let {
        println(it)
        if (it != 23) error("Example 1 failed")
    }
    Runner.solve(2024, 22, part2 = Day22::part2)
}