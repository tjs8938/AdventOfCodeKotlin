package AdventOfCodeKotlin.puzzles.AoC2023

import AdventOfCodeKotlin.framework.ExamplePuzzle
import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner
import java.util.EnumMap


class Day03 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {
            val input = puzzle.getAsString()
            val regex = Regex("""([0-9]+)""")
            var sum = 0
            input.forEachIndexed { rowIndex, row ->
                regex.findAll(row).forEach {
                    var start = it.range.start
                    var end = it.range.endInclusive
                    var surroundingChars = mutableSetOf<Char>()
                    if (start > 0) {
                        start--
                        surroundingChars.add(input[rowIndex][start])
                    }
                    if (end < row.length - 1) {
                        end++
                        surroundingChars.add(input[rowIndex][end])
                    }
                    if (rowIndex > 0) {
                        surroundingChars.addAll(
                            input[rowIndex - 1].subSequence(start, end + 1).asIterable()
                        )
                    }
                    if (rowIndex < input.size - 1) {
                        surroundingChars.addAll(
                            input[rowIndex + 1].subSequence(start, end + 1).asIterable()
                        )
                    }

                    if (surroundingChars.size > 1) {
                        sum += it.value.toInt()
                    }
                }
            }

            return sum.toString()
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            val input = puzzle.getAsString()
            val regex = Regex("""([0-9]+)""")
            var sum = 0
            val starLocations =
                mutableMapOf<Pair<Int, Int>, MutableList<Int>>().withDefault { mutableListOf() }
            input.forEachIndexed { rowIndex, row ->
                regex.findAll(row).forEach {
                    var start = it.range.start
                    var end = it.range.endInclusive
                    val element = it.value.toInt()
                    if (start > 0) {
                        start--
                        if (input[rowIndex][start] == '*') {
                            starLocations.putIfAbsent(rowIndex to start, mutableListOf())
                            starLocations[rowIndex to start]?.add(element)
                        }
                    }
                    if (end < row.length - 1) {
                        end++
                        if (input[rowIndex][end] == '*') {
                            starLocations.putIfAbsent(rowIndex to end, mutableListOf())
                            starLocations[rowIndex to end]?.add(element)
                        }
                    }
                    if (rowIndex > 0) {
                        input[rowIndex - 1].subSequence(start, end + 1).toList()
                            .forEachIndexed { index, c ->
                                if (c == '*') {
                                    starLocations.putIfAbsent(
                                        rowIndex - 1 to start + index,
                                        mutableListOf()
                                    )
                                    starLocations[rowIndex - 1 to start + index]?.add(element)
                                }
                            }
                    }
                    if (rowIndex < input.size - 1) {
                        input[rowIndex + 1].subSequence(start, end + 1).toList()
                            .forEachIndexed { index, c ->
                                if (c == '*') {
                                    starLocations.putIfAbsent(
                                        rowIndex + 1 to start + index,
                                        mutableListOf()
                                    )
                                    starLocations[rowIndex + 1 to start + index]?.add(element)
                                }
                            }
                    }
                }
            }

            return starLocations.filter { it.value.size == 2 }
                .map { it.value[0] * it.value[1] }
                .sum().toString()
        }
    }
}


fun main() {

    val example1 = ExamplePuzzle(
        """
        467..114..
        ...*......
        ..35..633.
        ......#...
        617*......
        .....+.58.
        ..592.....
        ......755.
        ...${'$'}.*....
        .664.598..
    """.trimIndent()
    )

//    assert(Day03.part1(example1) == "4361")
//    Runner.solve(2023, 3, part1 = Day03::part1)


//    assert(Day03.part2(example1) == "467835")

    val ex2 = ExamplePuzzle("""
        617*......
        ....58....
    """.trimIndent())
    assert(Day03.part2(ex2) == "35786")
    Runner.solve(2023, 3, part2 = Day03::part2)
}