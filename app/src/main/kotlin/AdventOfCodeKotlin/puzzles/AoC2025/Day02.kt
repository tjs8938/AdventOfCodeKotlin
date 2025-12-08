package AdventOfCodeKotlin.puzzles.AoC2025

import AdventOfCodeKotlin.oldframework.ExamplePuzzle
import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

class Day02 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {
            val ranges = puzzle.get().split(",").map {
                val (start, end) = it.split("-")
                start to end
            }

            var result = 0L

            ranges.forEach { (start, end) ->
                val halfLength = ceil(start.length / 2.0).toInt()
                val sum = sumOfRepeats(start, end, "1" + "0".repeat(halfLength - 1))
                result += sum
//                println("Sum for range $start-$end: $sum")
                println()
            }

            return result.toString()
        }

        private fun sumOfRepeats(start: String, end: String, minPrefix: String): Long {
            var result = 0L
            val minPrefixStr = minPrefix.repeat(ceil(start.length.toDouble() / minPrefix.length).toInt())
            val minPrefixNum = minPrefixStr.toLong()
            val maxPrefixNum = "9".repeat(minPrefixStr.length).toLong()
            println("Range: $start-$end, MinPrefixNum: $minPrefixNum, MaxPrefixNum: $maxPrefixNum")
            val increment = minPrefix.toLong() * 10 + 1
            var rangeStart = max(minPrefixNum, (floor(start.toDouble() / increment) * increment).toLong())

            if (rangeStart < start.toLong()) {
                rangeStart += increment
            }
            val rangeEnd = min(end.toLong(), maxPrefixNum)
            if (rangeStart <= rangeEnd && (start.toLong() <= rangeStart || end.toLong() >= rangeEnd)) {
                println("  Adjusted Range: $rangeStart-$rangeEnd")

                for (num in rangeStart..rangeEnd step increment) {
                    result += num
                }
            }
            return result
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            val ranges = puzzle.get().split(",").map {
                val (start, end) = it.split("-")
                start.toLong()..end.toLong()
            }

            var result = 0L
            ranges.forEach { range ->
                range.forEach { id ->
                    val idStr = id.toString()
                    for (i in 1 until idStr.length) {
                        if (idStr.length % i == 0) {
                            val prefix = idStr.take(i)
                            if (idStr == prefix.repeat(idStr.length / i)) {
//                                println(id)
                                result += id
                                break
                            }
                        }
                    }
                }
            }
            return result.toString()
        }
    }
}

fun main() {
    val puzzle = ExamplePuzzle(
        """
        11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124
    """.trimIndent()
    )
    if (Day02.part1(puzzle) != "1227775554") {
        println("Part 1 example failed")
        return
    }
    Runner.solve(2025, 2, part1 = Day02::part1)

    if (Day02.part2(puzzle) != "4174379265") {
        println("Part 2 example failed")
        return
    }
    Runner.solve(2025, 2, part2 = Day02::part2)
}
