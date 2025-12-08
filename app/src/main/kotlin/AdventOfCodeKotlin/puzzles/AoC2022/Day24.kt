package AdventOfCodeKotlin.puzzles.AoC2022

import AdventOfCodeKotlin.oldframework.ExamplePuzzle
import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner


class Day24 {
    companion object {

        val directions: Map<Char?, Pair<Int, Int>> = mapOf('>' to (1 to 0), 'v' to (0 to 1), '<' to (-1 to 0), '^' to ( 0 to -1), null to (0 to 0))
        fun parseInput(puzzle: PuzzleInputProvider) : Snowfield {
            val input = puzzle.getAsString()

            val startLocation = input[0].indexOf('.') to 0
            val endLocation = input.last().indexOf('.') to (input.size - 1)
            val bottomCorner = input.last().length - 2 to input.size - 2

            val snowfield = Snowfield(startLocation, endLocation, bottomCorner)
            input.forEachIndexed { y, row ->
                row.forEachIndexed { x, c ->
                    if (c != '.' && c != '#') {
                        snowfield.field[x to y] = mutableListOf(c)
                    }
                }
            }

            return snowfield
        }

        fun part1(puzzle: PuzzleInputProvider): String {

            val startState = parseInput(puzzle)

            return getToTheEnd(startState)?.second.toString()
        }

        private fun getToTheEnd(startState: Snowfield) : Pair<Snowfield, Int>? {
            val snowHistory = SnowfieldStorage(startState)

            val positionHistory = mutableSetOf<State>()
            val states = ArrayDeque(listOf(startState.startLocation to 0))

            while (states.isNotEmpty()) {
                val s = states.removeFirst()

                val newTime = s.second + 1
                val snowfield = snowHistory.getSnowfield(newTime)
                directions.values.forEach { d ->
                    val newLoc = d + s.first
                    if (newLoc == snowfield.endLocation) {
                        return snowfield to newTime
                    } else if (!positionHistory.contains(newLoc to newTime) &&
                        !snowfield.field.containsKey(newLoc) &&
                        (1..snowfield.bottomCorner.first).contains(newLoc.first) &&
                        (1..snowfield.bottomCorner.second).contains(newLoc.second)
                    ) {
                        states.add(newLoc to newTime)
                        positionHistory.add(newLoc to newTime)
                    } else if (s.first == snowfield.startLocation && newLoc == snowfield.startLocation) {
                        // allow waiting in the start location, but no moving back
                        states.add(s.first to newTime)
                        positionHistory.add(s.first to newTime)
                    }
                }
            }
            return null
        }

        fun part2(puzzle: PuzzleInputProvider): String {

            val startState = parseInput(puzzle)

            val endTheFirstTime = getToTheEnd(startState)!!
            val backToTheStart = endTheFirstTime.first.let {
                getToTheEnd(it.copy(startLocation = it.endLocation, endLocation = it.startLocation))!!
            }

            val toTheEndAgain = backToTheStart.first.let {
                getToTheEnd(it.copy(startLocation = it.endLocation, endLocation = it.startLocation))!!
            }

            return (endTheFirstTime.second + backToTheStart.second + toTheEndAgain.second).toString()
        }
    }
}

typealias State = Pair<Pair<Int, Int>, Int>

class SnowfieldStorage(startState: Snowfield) {
    val states: MutableList<Snowfield>

    init {
        states = mutableListOf(startState)
    }

    fun getSnowfield(minute: Int) : Snowfield {
        return if (minute < states.size) {
            states[minute]
        } else {
            val nextState = states.last().nextState()
            states.add(nextState)
            nextState
        }
    }
}

data class Snowfield(val startLocation: Pair<Int, Int>, val endLocation: Pair<Int, Int>, val bottomCorner: Pair<Int, Int>, val field: MutableMap<Pair<Int, Int>, MutableList<Char>> = mutableMapOf()) {

    fun nextState() : Snowfield {
        val newState = this.copy(field = mutableMapOf())

        field.forEach { (oldLoc, cList) ->
            cList.forEach { c ->
                var (oldX, oldY) = oldLoc + Day24.directions[c]!!
                if (oldX < 1) {
                    oldX = bottomCorner.first
                } else if (oldX > bottomCorner.first) {
                    oldX = 1
                }

                if (oldY < 1) {
                    oldY = bottomCorner.second
                } else if (oldY > bottomCorner.second) {
                    oldY = 1
                }

                val newLoc = oldX to oldY

                if (newState.field.containsKey(newLoc)) {
                    newState.field[newLoc]!!.add(c)
                } else {
                    newState.field[newLoc] = mutableListOf(c)
                }
            }
        }

        return newState
    }
}

fun main() {

    val example = ExamplePuzzle("#.######\n" +
        "#>>.<^<#\n" +
        "#.<..<<#\n" +
        "#>v.><>#\n" +
        "#<^v^^>#\n" +
        "######.#")

    assert(Day24.part1(example) == "18")
    Runner.solve(2022, 24, part1 = Day24::part1)

    assert(Day24.part2(example) == "54")
    Runner.solve(2022, 24, part2 = Day24::part2)
}

