package AdventOfCodeKotlin.puzzles.AoC2023

import AdventOfCodeKotlin.oldframework.ExamplePuzzle
import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner
import AdventOfCodeKotlin.puzzles.AoC2023.Day10.Directions
import AdventOfCodeKotlin.puzzles.AoC2023.Day10.Directions.EAST
import AdventOfCodeKotlin.puzzles.AoC2023.Day10.Directions.SOUTH


class Day17 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {

            val blocks = puzzle.getAsString()
            val activeStates = mutableSetOf(State(0 to 0, EAST, -1) to 0)
            val bestPaths = mutableMapOf<State, Int>()

            while (activeStates.isNotEmpty()) {
                val (state, heatloss) = activeStates.pop()
                Directions.values().forEach {
                    if ((Directions.values().indexOf(it) + 2) % 4 != Directions.values()
                            .indexOf(state.direction)
                    ) {
                        val nextLocation = it.plus(state.location)
                        if (blocks.contains(nextLocation)) {
                            val dirCount = if (it != state.direction) {
                                0
                            } else {
                                state.straightCount + 1
                            }
                            if (dirCount < 3) {
                                val newState = State(
                                    nextLocation,
                                    it,
                                    dirCount
                                )
                                val newHeatloss = heatloss + calcHeatLoss(nextLocation, blocks)
                                if (!bestPaths.containsKey(newState) || bestPaths[newState]!! > newHeatloss) {
                                    bestPaths[newState] = newHeatloss
                                    activeStates.add(newState to newHeatloss)
                                }
                            }
                        }
                    }
                }
            }

            return bestPaths.filterKeys { it.location == blocks.size - 1 to blocks[0].length - 1 }
                .minOf { it.value }.toString()
        }

        private fun calcHeatLoss(location: Pair<Int, Int>, blocks: List<String>): Int {
            return blocks[location].digitToInt()
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            val blocks = puzzle.getAsString()
            val activeStates = mutableSetOf(
                State(0 to 0, EAST, 0) to 0,
                State(0 to 0, SOUTH, 0) to 0
            )
            val bestPaths = mutableMapOf<State, Int>()

            while (activeStates.isNotEmpty()) {
                val (state, heatloss) = activeStates.pop()
                val directions: List<Directions> = if (state.straightCount < 4) {
                    listOf(state.direction)
                } else {
                    Directions.values().filterNot { it == state.direction.opposite() }.toList()
                }
                directions.forEach {
                    val nextLocation = it.plus(state.location)
                    if (blocks.contains(nextLocation)) {
                        val dirCount = if (it != state.direction) {
                            1
                        } else {
                            state.straightCount + 1
                        }
                        if (dirCount <= 10) {
                            val newState = State(
                                nextLocation,
                                it,
                                dirCount,
                                state
                            )
                            val newHeatloss = heatloss + calcHeatLoss(nextLocation, blocks)
                            if (!bestPaths.containsKey(newState) || bestPaths[newState]!! > newHeatloss) {
                                bestPaths[newState] = newHeatloss
                                activeStates.add(newState to newHeatloss)
                            }
                        }
                    }
                }
            }

            return bestPaths.filterKeys {
                it.location == blocks.size - 1 to blocks[0].length - 1 && it.straightCount in 4..10
            }.minOf { it.value }.toString()
        }
    }
}

fun <T> MutableSet<T>.pop(): T = this.first().also{this.remove(it)}

data class State(
    val location: Pair<Int, Int>,
    val direction: Directions,
    val straightCount: Int
) {

    var parent: State? = null

    constructor(location: Pair<Int, Int>, direction: Directions, straightCount: Int, p: State) :
    this(location, direction, straightCount) {
        parent = p
    }
//    fun print() {
//        if (parent != null) {
//            parent.print()
//            print(" -> ")
//        }
//        print(this.location)
//    }
}


fun main() {

    val ex1 = ExamplePuzzle(
        """
        2413432311323
        3215453535623
        3255245654254
        3446585845452
        4546657867536
        1438598798454
        4457876987766
        3637877979653
        4654967986887
        4564679986453
        1224686865563
        2546548887735
        4322674655533
    """.trimIndent()
    )

    assert(Day17.part1(ex1) == "102")
    assert(Day17.part2(ex1) == "94")

    val ex2 = ExamplePuzzle("""
        111111111111
        999999999991
        999999999991
        999999999991
        999999999991
    """.trimIndent())

    assert(Day17.part2(ex2) == "71")
//    Runner.solve(2023, 17, part1 = Day17::part1)
    Runner.solve(2023, 17, part2 = Day17::part2)
}