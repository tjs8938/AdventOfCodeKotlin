package AdventOfCodeKotlin.puzzles.AoC2022

import AdventOfCodeKotlin.oldframework.ExamplePuzzle
import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner
import java.math.BigInteger


class Day17 {
    companion object {

        val falling = listOf(
            listOf(2 to 0, 3 to 0, 4 to 0, 5 to 0),
            listOf(3 to 2, 2 to 1, 3 to 1, 4 to 1, 3 to 0),
            listOf(4 to 2, 4 to 1, 4 to 0, 3 to 0, 2 to 0),
            listOf(2 to 3, 2 to 2, 2 to 1, 2 to 0),
            listOf(2 to 1, 3 to 1, 2 to 0, 3 to 0)
        )
        data class State(val rocks: MutableSet<Pair<Int, Int>>, var rounds: Int, val movements: String, var moveIndex: Int ) {

            fun maxHeight(): Int {
                return rocks.maxOf { it.second }
            }
            fun maxRelativeHeights() : List<Int> {
                val max = maxHeight()
                return (0 .. 6 ).map { x ->
                    max - (rocks.filter { it.first == x }.maxOfOrNull { it.second } ?: 0)
                }
            }
        }

        private fun dropRock(state: State) {
            // Find the starting height of the next falling rock
            val startingHeight = (state.rocks.maxOfOrNull { it.second } ?: -1) + 4

            // Copy the next rock to fall
            var fallingRock = falling[state.rounds % 5].map { it.first to it.second + startingHeight }

            var resting = false
            while (!resting) {
                // try to move the rock to the side
                val dir = if (state.movements[state.moveIndex] == '<') {
                    -1
                } else {
                    1
                }
                state.moveIndex = (state.moveIndex + 1) % state.movements.length
                var tempRock = fallingRock.map { it.first + dir to it.second }
                if (tempRock.none { it.first < 0 || it.first > 6 || state.rocks.contains(it) }) {
                    fallingRock = tempRock
                }

                // try to move the rock down
                tempRock = fallingRock.map { it.first to it.second - 1 }
                if (tempRock.none { it.second < 0 || state.rocks.contains(it) }) {
                    fallingRock = tempRock
                } else {
                    resting = true
                    state.rocks.addAll(fallingRock)
                }
            }
            state.rounds++
        }

        fun part1(puzzle: PuzzleInputProvider): String {

            val state  = State(mutableSetOf(), 0, puzzle.get(), 0)

            while (state.rounds < 2022) {
                dropRock(state)
            }

            return state.rocks.maxOfOrNull { it.second + 1 }.toString()
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            /**
             * (rockIndex, moveIndex) ->
             *      List(max y - (y at x location)) -> (number of rocks that have fallen, max height)
             */
            val history: MutableMap<Pair<Int, Int>, MutableMap<List<Int>, Pair<Int, Int>>> = mutableMapOf()
            val heights = mutableListOf(0)

            val state  = State(mutableSetOf(), 0, puzzle.get(), 0)

            while (true) {
                dropRock(state)

                val key = (state.rounds % 5) to state.moveIndex
                val topography = state.maxRelativeHeights()
                if (!history.contains(key)) {
                    history[key] = mutableMapOf()
                }

                history[key]!!.let { previousTopographies ->


                    if (previousTopographies.contains(topography)) {
                        val cycleHeight = BigInteger.valueOf((state.maxHeight() - previousTopographies[topography]!!.second).toLong())
                        val cycleCount = BigInteger.valueOf((state.rounds - previousTopographies[topography]!!.first).toLong())
                        val rounds = BigInteger("1000000000000")
                        var fullCycles = rounds.divide(BigInteger.valueOf(cycleCount.toLong()))
                        var cycleStart = rounds.mod(BigInteger.valueOf(cycleCount.toLong()))

                        while (cycleStart.toInt() + cycleCount.toInt() < state.rounds) {
                            cycleStart += cycleCount
                            fullCycles -= BigInteger.ONE
                        }

                        val totalHeight = fullCycles.times(cycleHeight).plus(BigInteger.valueOf(heights[cycleStart.toInt()].toLong() + 1))
                        return totalHeight.toString()
                    } else {
                        previousTopographies[topography] = state.rounds to state.maxHeight()
                        heights.add(state.maxHeight())
                    }
                }
            }

            return ""
        }
    }
}


fun main() {

    val example = ExamplePuzzle(">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>")
//    println(Day17.part1(example))

//    Runner.solve(2022, 17, part1 = Day17::part1)

    assert(Day17.part2(example) == "1514285714288")

    Runner.solve(2022, 17, part2 = Day17::part2)
}

