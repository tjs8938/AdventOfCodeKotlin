package AdventOfCodeKotlin.puzzles.AoC2023

import AdventOfCodeKotlin.oldframework.ExamplePuzzle
import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner
import AdventOfCodeKotlin.puzzles.AoC2023.Day10.*
import AdventOfCodeKotlin.puzzles.AoC2023.Day10.Directions.EAST
import AdventOfCodeKotlin.puzzles.AoC2023.Day10.Directions.NORTH
import AdventOfCodeKotlin.puzzles.AoC2023.Day10.Directions.SOUTH
import AdventOfCodeKotlin.puzzles.AoC2023.Day10.Directions.WEST


class Day16 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {

            val mirrors = puzzle.getAsString()

            return calcEnergy(mirrors, 0 to 0, EAST).toString()
        }

        fun calcEnergy(mirrors: List<String>, start: Pair<Int, Int>, direction: Directions) : Int {
            val visited = mutableMapOf(start to mutableSetOf(direction))
            val current = mutableListOf(start to direction)

            while (current.isNotEmpty()) {
                val next = current.removeAt(0)
                val directions = turns[next.second]!!.getOrDefault(mirrors[next.first], listOf(next.second))
                directions.forEach { dir ->
                    val moveTo = dir.plus(next.first)
                    if (mirrors.contains(moveTo) && !visited.contains(moveTo, dir)) {
                        val visitedDirList = visited.getOrPut(moveTo) { mutableSetOf() }
                        visitedDirList.add(dir)
                        current.add(moveTo to dir)
                    }
                }
            }

            return visited.keys.size
        }

        fun part2(puzzle: PuzzleInputProvider): String {

            val mirrors = puzzle.getAsString()
            val entryPoints = mutableListOf<Pair<Pair<Int, Int>, Directions>>()
            mirrors.indices.forEach { rowIndex ->
                entryPoints.add((rowIndex to 0) to EAST)
                entryPoints.add((rowIndex to mirrors[rowIndex].length - 1) to WEST)
            }
            mirrors[0].indices.forEach { columnIndex ->
                entryPoints.add((0 to columnIndex) to SOUTH)
                entryPoints.add((mirrors.size - 1 to columnIndex) to NORTH)
            }


            return entryPoints.maxOf { calcEnergy(mirrors, it.first, it.second) }.toString()
        }

        val turns = mapOf(
            EAST  to mapOf('/' to listOf(NORTH), '\\' to listOf(SOUTH), '|' to listOf(NORTH, SOUTH)),
            WEST  to mapOf('/' to listOf(SOUTH), '\\' to listOf(NORTH), '|' to listOf(NORTH, SOUTH)),
            NORTH to mapOf('/' to listOf(EAST),  '\\' to listOf(WEST),  '-' to listOf(EAST, WEST)),
            SOUTH to mapOf('/' to listOf(WEST),  '\\' to listOf(EAST),  '-' to listOf(EAST, WEST))
        )
    }
}

fun List<String>.contains(location: Pair<Int, Int>) : Boolean {
    return indices.contains(location.first) && get(location.first).indices.contains(location.second)
}

private fun MutableMap<Pair<Int, Int>, MutableSet<Directions>>.contains(location: Pair<Int, Int>, direction: Directions) : Boolean {
    return containsKey(location) && get(location)!!.contains(direction)
}


fun main() {

    val ex1 = ExamplePuzzle("""
        .|...\....
        |.-.\.....
        .....|-...
        ........|.
        ..........
        .........\
        ..../.\\..
        .-.-/..|..
        .|....-|.\
        ..//.|....
    """.trimIndent())

    assert(Day16.part1(ex1) == "46")
    assert(Day16.part2(ex1) == "51")
    Runner.solve(2023, 16, part1 = Day16::part1)
    Runner.solve(2023, 16, part2 = Day16::part2)
}