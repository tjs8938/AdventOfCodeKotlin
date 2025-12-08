package AdventOfCodeKotlin.puzzles.AoC2023

import AdventOfCodeKotlin.oldframework.ExamplePuzzle
import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner
import java.math.BigInteger
import kotlin.math.floor


class Day04 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {
            return puzzle.getAsString().map { Card(it) }.map {
                floor(Math.pow(2.0, (it.myNumbers.intersect(it.winningNumbers).size - 1).toDouble())).toInt()
            }.sum().toString()
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            val cards = puzzle.getAsString().map { Card(it) }
            val counts = cards.map { BigInteger.valueOf(1) }.toMutableList()
            cards.map {
                it.myNumbers.intersect(it.winningNumbers).size
            }.forEachIndexed { index, winners ->
                (index + 1..index + winners).forEach { counts[it] += counts[index] }
            }
            return counts.reduce(BigInteger::add).toString()
        }
    }
}

class Card(input: String) {
    var cardNumber: Int = 0
    val winningNumbers = mutableSetOf<Int>()
    val myNumbers = mutableSetOf<Int>()

    init {
        cardNumber = input.split(":")[0].substring(5).trim().toInt()
        winningNumbers.addAll((input.split(":")[1].split("|")[0]).trim().split(" ").filter { it != "" }.map { it.toInt() })
        myNumbers.addAll((input.split(":")[1].split("|")[1]).trim().split(" ").filter { it != "" }.map { it.toInt() })
    }
}


fun main() {

    val ex1 = ExamplePuzzle("""
        Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
        Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
        Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
        Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
        Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
        Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
    """.trimIndent())

    val ans1 = Day04.part1(ex1)
    println(ans1)
    assert(ans1 == "13")
//    Runner.solve(2023, 4, part1 = Day04::part1)

    println(Day04.part2(ex1))
    assert(Day04.part2(ex1) == "30")
    Runner.solve(2023, 4, part2 = Day04::part2)
}