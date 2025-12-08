package AdventOfCodeKotlin.puzzles.AoC2023

import AdventOfCodeKotlin.oldframework.ExamplePuzzle
import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner
import java.lang.StringBuilder
import kotlin.math.abs


class Day11 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {

            var space = puzzle.getAsString().toMutableList()
            val emptyRows = space.mapIndexed { index, row -> index to row }
                .filter { it.second.toSet().size == 1 }
                .map { it.first }

            emptyRows.reversed().forEach {
                space.add(it, ".".repeat(space[0].length))
            }

            val emptyColumns = (0 until space[0].length).filter { column ->
                space.all { it[column] == '.' }
            }.reversed()

            space = space.map {
                val sb = StringBuilder(it)
                emptyColumns.forEach { c -> sb.insert(c, '.') }
                sb.toString()
            }.toMutableList()

            val locations = space.flatMapIndexed { rowIndex: Int, s: String ->
                s.mapIndexed { columnIndex, c ->
                    Triple(columnIndex, rowIndex, c)
                }
            }.filter { it.third == '#' }
                .map { it.first to it.second }

            return locations.flatMapIndexed { index: Int, pair: Pair<Int, Int> ->
                locations.subList(index + 1, locations.size).map { pair to it }
            }
                .sumOf { abs(it.first.first - it.second.first) + abs(it.first.second - it.second.second) }
                .toString()
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            return expanded(puzzle, 1000000)
        }

        fun expanded(puzzle: PuzzleInputProvider, extraSpace: Int): String {
            val space = puzzle.getAsString().toMutableList()
            val emptyRows = space.mapIndexed { index, row -> index to row }
                .filter { it.second.toSet().size == 1 }
                .map { it.first }

            val emptyColumns = (0 until space[0].length).filter { column ->
                space.all { it[column] == '.' }
            }.reversed()

            val locations = space.flatMapIndexed { rowIndex: Int, s: String ->
                s.mapIndexed { columnIndex, c ->
                    Triple(columnIndex, rowIndex, c)
                }
            }.filter { it.third == '#' }
                .map { it.first to it.second }

            return locations.flatMapIndexed { index: Int, pair: Pair<Int, Int> ->
                locations.subList(index + 1, locations.size).map { pair to it }
            }
                .sumOf {
                    val (g1, g2) = it
                    val left = minOf(g1.first, g2.first)
                    val right = maxOf(g1.first, g2.first)
                    val top = minOf(g1.second, g2.second)
                    val bottom = maxOf(g1.second, g2.second)

                    var dist = abs(g1.first - g2.first) + abs(g1.second - g2.second).toLong()

                    dist += emptyColumns
                        .filter { it in (left + 1) until right }
                        .count() * (extraSpace - 1)

                    dist += emptyRows
                        .filter { it in (top + 1) until bottom }
                        .count() * (extraSpace - 1)

                    dist
                }
                .toString()
        }
    }
}


fun main() {

    val ex1 = ExamplePuzzle("""
        ...#......
        .......#..
        #.........
        ..........
        ......#...
        .#........
        .........#
        ..........
        .......#..
        #...#.....
    """.trimIndent())

//    assert(Day11.part1(ex1) == "374")
//    Runner.solve(2023, 11, part1 = Day11::part1)


    println(Day11.expanded(ex1, 10))
    assert(Day11.expanded(ex1, 10) == "1030")
    assert(Day11.expanded(ex1, 100) == "8410")
    Runner.solve(2023, 11, part2 = Day11::part2)
}