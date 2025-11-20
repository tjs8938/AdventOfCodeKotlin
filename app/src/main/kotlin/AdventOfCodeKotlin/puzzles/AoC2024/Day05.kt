package AdventOfCodeKotlin.puzzles.AoC2024

import AdventOfCodeKotlin.framework.ExamplePuzzle
import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner

class Day05 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): Any {
            val (orderInput, updateInput) = puzzle.get().split("\n\n")

            val orderPairs = orderInput.split("\n").map { it.split("|").map { it.toInt() } }.map { it[0] to it[1] }
            val updateLists = updateInput.split("\n").map { it.split(",").map { it.toInt() } }

            val orders = orderPairs.groupBy { it.first }.mapValues { it.value.map { it.second } }

            val middleNumbers = updateLists.filter { list ->
                list.indices.all { index ->
                    orders[list[index]]?.let { after ->
                        try {
                            list.subList(0, index).intersect(after.toSet()).isEmpty()
                        } catch (e: Exception) {
                            true
                        }
                    } ?: true
                }
            }
                .map { it[it.size / 2] }

            return middleNumbers.sum()
        }

        fun part2(puzzle: PuzzleInputProvider): Any {
            val (orderInput, updateInput) = puzzle.get().split("\n\n")

            val orderPairs = orderInput.split("\n").map { it.split("|").map { it.toInt() } }.map { it[0] to it[1] }
            val updateLists = updateInput.split("\n").map { it.split(",").map { it.toInt() } }

            val orders = orderPairs.groupBy { it.first }.mapValues { it.value.map { it.second } }

            val filtered = updateLists.filter { list ->
                list.indices.any { index ->
                    orders[list[index]]?.let { after ->
                        try {
                            list.subList(0, index).intersect(after).isNotEmpty()
                        } catch (e: Exception) {
                            false
                        }
                    } ?: false
                }
            }
            val sorted = filtered.map {
                it.sortedWith { a: Int, b: Int ->
                    if (a in orders && b in orders[a]!!) {
                        -1
                    } else if (b in orders && a in orders[b]!!) {
                        1
                    } else {
                        0
                    }
                }
            }
            val middleNumbers = sorted.map { it[it.size / 2] }

            return middleNumbers.sum()
        }
    }
}

fun main() {
    val example = ExamplePuzzle(
        """
        47|53
        97|13
        97|61
        97|47
        75|29
        61|13
        75|53
        29|13
        97|29
        53|29
        61|53
        97|53
        61|29
        47|13
        75|47
        97|75
        47|61
        75|61
        47|29
        75|13
        53|13

        75,47,61,53,29
        97,61,53,29,13
        75,29,13
        75,97,47,61,53
        61,13,29
        97,13,75,29,47
    """.trimIndent()
    )

    println(Day05.part1(example))
    println(Day05.part2(example))
    Runner.solve(2024, 5, part1 = Day05::part1)
    Runner.solve(2024, 5, part2 = Day05::part2)
}