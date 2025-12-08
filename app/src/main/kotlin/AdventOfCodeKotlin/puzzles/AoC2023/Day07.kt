package AdventOfCodeKotlin.puzzles.AoC2023

import AdventOfCodeKotlin.oldframework.ExamplePuzzle
import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner


class Day07 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {
            return solve(puzzle) { Camel(it) }
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            return solve(puzzle) { Camel2(it) }
        }

        fun solve(puzzle: PuzzleInputProvider, factory: (String) -> Camel): String {
            return puzzle.getAsString()
                .map { factory(it) }
                .sortedDescending()
                .mapIndexed { strength, camel ->
                    (strength + 1) * camel.points
                }
                .sum()
                .toString()
        }
    }
}

open class Camel(details: String) : Comparable<Camel> {
    var hand: String
    var points: Int
    var counts: Map<Char, Int>

    init {
        val split = details.split(" ")
        hand = split[0]
        points = split[1].toInt()
        counts = hand.groupingBy { it }.eachCount()
    }

    override fun compareTo(other: Camel): Int {
       return if (counts.size != other.counts.size) { // fewer distinct cards means a better hand type
           counts.size - other.counts.size
       } else if (other.counts.values.max() != counts.values.max()) { // higher count of the most frequent card means a better hand type
           other.counts.values.max() - counts.values.max()
       } else { // same hand type, compare the first pair of cards that differs
           val diffPair = hand.zip(other.hand).first { it.first != it.second }
           getCardOrder().indexOf(diffPair.first) - getCardOrder().indexOf(diffPair.second)
       }
    }

    open fun getCardOrder() = cards

    companion object {
        val cards = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
    }
}

class Camel2(details: String) : Camel(details) {
    private var jokerCount: Int = hand.count { it == 'J' }
    private var countSize: Int

    init {
        counts = hand.filter { it != 'J' }.groupingBy { it }.eachCount().mapValues { it.value + jokerCount }
        countSize = counts.size
        if (countSize == 0) {
            countSize = 1
            counts = mapOf('J' to 5)
        }
    }
    override fun getCardOrder() = cards

    companion object {
        val cards = listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J')
    }
}


fun main() {

    val ex1 = ExamplePuzzle("""
        32T3K 765
        T55J5 684
        KK677 28
        KTJJT 220
        QQQJA 483
    """.trimIndent())

    assert(Day07.part1(ex1) == "6440")
//    Runner.solve(2023, 7, part1 = Day07::part1)
    assert(Day07.part2(ex1) == "5905")
    Runner.solve(2023, 7, part2 = Day07::part2)
}