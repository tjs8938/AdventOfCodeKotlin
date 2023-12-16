package AdventOfCodeKotlin.puzzles.AoC2023

import AdventOfCodeKotlin.framework.ExamplePuzzle
import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner


class Day14 {
    companion object {

        val ROUND = 'O'
        val EMPTY = '.'
        fun part1(puzzle: PuzzleInputProvider): String {

            val rockField = puzzle.getAsString().map { it.toMutableList() }
            var load = 0

            rockField.forEachIndexed { rowIndex, row ->
                row.forEachIndexed { columnIndex, c ->
                    if (c == ROUND) {
                        var moveIndex = rowIndex
                        while (moveIndex > 0 && rockField[moveIndex - 1][columnIndex] == EMPTY) {
                            moveIndex--
                        }
                        rockField[rowIndex][columnIndex] = EMPTY
                        rockField[moveIndex][columnIndex] = ROUND

                        load += (rockField.size - moveIndex)
                    }
                }
            }

            return load.toString()
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            val rockField = puzzle.getAsString().map { it.toMutableList() }


            var load = cycle(rockField)
            var positions = rockField.joinToString { it.joinToString() }
            var cycleCount = 1L
            val loadToCycleCount = mutableMapOf<String, Long>()
            val allLoads = mutableListOf<Int>()
            while (!loadToCycleCount.containsKey(positions)) {
                loadToCycleCount[positions] = cycleCount
                allLoads.add(load)
                load = cycle(rockField)
                positions = rockField.joinToString { it.joinToString() }
                cycleCount++
            }

            println("Current cycle: $cycleCount")
            println("Previous cycle: " + loadToCycleCount[positions])

            val cycleStart = loadToCycleCount[positions]!!
            return allLoads[((1000000000 - cycleStart - 1) % (cycleCount - cycleStart) + cycleStart).toInt()].toString()
        }

        private fun cycle(rockField: List<MutableList<Char>>): Int {
            var load = 0

            // tilt NORTH
            rockField.forEachIndexed { rowIndex, row ->
                row.forEachIndexed { columnIndex, c ->
                    if (c == ROUND) {
                        var moveIndex = rowIndex
                        while (moveIndex > 0 && rockField[moveIndex - 1][columnIndex] == EMPTY) {
                            moveIndex--
                        }
                        rockField[rowIndex][columnIndex] = EMPTY
                        rockField[moveIndex][columnIndex] = ROUND
                    }
                }
            }

            // tilt WEST
            rockField.forEachIndexed { rowIndex, row ->
                row.forEachIndexed { columnIndex, c ->
                    if (c == ROUND) {
                        var moveIndex = columnIndex
                        while (moveIndex > 0 && rockField[rowIndex][moveIndex - 1] == EMPTY) {
                            moveIndex--
                        }
                        rockField[rowIndex][columnIndex] = EMPTY
                        rockField[rowIndex][moveIndex] = ROUND
                    }
                }
            }

            // tilt SOUTH
            rockField.indices.reversed().forEach { rowIndex ->
                rockField[rowIndex].indices.forEach { columnIndex ->
                    if (rockField[rowIndex][columnIndex] == ROUND) {
                        var moveIndex = rowIndex
                        while (moveIndex < rockField.size - 1 && rockField[moveIndex + 1][columnIndex] == EMPTY) {
                            moveIndex++
                        }
                        rockField[rowIndex][columnIndex] = EMPTY
                        rockField[moveIndex][columnIndex] = ROUND
                    }
                }
            }

            // tilt EAST
            rockField.indices.forEach { rowIndex ->
                rockField[rowIndex].indices.reversed().forEach { columnIndex ->
                    if (rockField[rowIndex][columnIndex] == ROUND) {
                        var moveIndex = columnIndex
                        while (moveIndex < rockField[0].size - 1 && rockField[rowIndex][moveIndex + 1] == EMPTY) {
                            moveIndex++
                        }
                        rockField[rowIndex][columnIndex] = EMPTY
                        rockField[rowIndex][moveIndex] = ROUND

                        load += (rockField.size - rowIndex)
                    }
                }
            }
            return load
        }
    }
}


fun main() {

    val ex1 = ExamplePuzzle("""
        O....#....
        O.OO#....#
        .....##...
        OO.#O....O
        .O.....O#.
        O.#..O.#.#
        ..O..#O..O
        .......O..
        #....###..
        #OO..#....
    """.trimIndent())

//    println(Day14.part1(ex1))
//    assert(Day14.part1(ex1) == "136")
//    Runner.solve(2023, 14, part1 = Day14::part1)

    assert(Day14.part2(ex1) == "64")
    Runner.solve(2023, 14, part2 = Day14::part2)
}