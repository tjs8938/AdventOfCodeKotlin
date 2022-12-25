package AdventOfCodeKotlin.puzzles.AoC2022

import AdventOfCodeKotlin.framework.ExamplePuzzle
import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner
import java.math.BigInteger


class Day25 {
    companion object {
        val decoder = mapOf('0' to 0, '1' to 1, '2' to 2, '=' to -2, '-' to -1)
        val encoder = decoder.map { (k, v) -> v to k }.toMap()

        fun snafuToInt(snafu: String) : BigInteger {
            var value = BigInteger.ZERO
            snafu.forEach { c ->
                value *= BigInteger("5")
                value += BigInteger.valueOf(decoder[c]!!.toLong())
            }
            return value
        }

        fun intToSnafu(num: BigInteger) : String {
            var remaining = num
            val placeValues = mutableListOf<Int>()
            while (remaining > BigInteger.ZERO) {
                val rem = remaining.mod(BigInteger("5"))
                placeValues.add(rem.toInt())
                remaining = (remaining - rem) / BigInteger("5")
            }

            placeValues.indices.forEach { i ->
                if (placeValues[i] > 2) {
                    placeValues[i] -= 5
                    if (i == placeValues.size) {
                        placeValues.add(1)
                    } else {
                        placeValues[i+1] += 1
                    }
                }
            }

            return placeValues.reversed().map { encoder[it] }.joinToString(separator = "")
        }
        fun part1(puzzle: PuzzleInputProvider): String {
            return intToSnafu(puzzle.getAsString().map (Day25::snafuToInt ).fold(BigInteger.ZERO, BigInteger::add))
        }
    }
}


fun main() {

    val example = ExamplePuzzle("1=-0-2\n" +
        "12111\n" +
        "2=0=\n" +
        "21\n" +
        "2=01\n" +
        "111\n" +
        "20012\n" +
        "112\n" +
        "1=-1=\n" +
        "1-12\n" +
        "12\n" +
        "1=\n" +
        "122")

    assert(Day25.part1(example) == "2=-1=0")
    Runner.solve(2022, 25, part1 = Day25::part1)
}

