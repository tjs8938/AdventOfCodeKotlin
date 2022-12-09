package AdventOfCodeKotlin.puzzles.AoC2022

import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner
import java.lang.Math.abs


class Day09 {
    companion object {

        val movements: Map<Char, Pair<Int, Int>> =
            mapOf('D' to (0 to 1), 'U' to (0 to -1), 'L' to (-1 to 0), 'R' to (1 to 0))

        private fun moveOther(
            lead: Pair<Int, Int>,
            following: Pair<Int, Int>
        ): Pair<Int, Int> {
            var newPosition = following
            if (abs(lead.first - following.first) == 2 && abs(lead.second - following.second) == 2) {
                newPosition = following.first + ((lead.first - following.first) / 2) to following.second + ((lead.second - following.second) / 2)
            } else if (lead.first - following.first > 1) {
                newPosition = lead.first - 1 to lead.second
            } else if (lead.first - following.first < -1) {
                newPosition = lead.first + 1 to lead.second
            } else if (lead.second - following.second > 1) {
                newPosition = lead.first to lead.second - 1
            } else if (lead.second - following.second < -1) {
                newPosition = lead.first to lead.second + 1
            }
            return newPosition
        }

        private fun moveHead(
            headPosition: Pair<Int, Int>,
            direction: Char
        ) =
            headPosition.first + movements[direction]!!.first to headPosition.second + movements[direction]!!.second

        fun part1(puzzle: PuzzleInputProvider): String {
            var headPosition = 0 to 0
            var tailPosition = 0 to 0
            val tailLocations = mutableSetOf(0 to 0)

            puzzle.getAsString().map { it[0] to it.substring(2).toInt() }
                .forEach { (direction, moves) ->
                    repeat(moves) {
                        headPosition = moveHead(headPosition, direction)
                        tailPosition = moveOther(headPosition, tailPosition)
                        tailLocations.add(tailPosition)
                    }
                }
            return tailLocations.size.toString()
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            val positions = MutableList(10) { 0 to 0 }
            val tailLocations = mutableSetOf(0 to 0)

            puzzle.getAsString().map { it[0] to it.substring(2).toInt() }
                .forEach { (direction, moves) ->
                    repeat(moves) {
                        positions[0] = moveHead(positions[0], direction)
                        (0 until positions.size - 1).forEach {
                            positions[it+1] = moveOther(positions[it], positions[it+1])
                        }
                        tailLocations.add(positions[9])
                    }
                }
            return tailLocations.size.toString()
        }
    }
}


fun main() {
    Runner.solve(2022, 9, part1 = Day09::part1)
    Runner.solve(2022, 9, part2 = Day09::part2)
}

