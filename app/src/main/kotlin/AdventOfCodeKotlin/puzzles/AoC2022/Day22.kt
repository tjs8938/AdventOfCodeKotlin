package AdventOfCodeKotlin.puzzles.AoC2022

import AdventOfCodeKotlin.oldframework.ExamplePuzzle
import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner


class Day22 {
    companion object {

        val directions = listOf(1 to 0, 0 to 1, -1 to 0, 0 to -1)
        val turn = mapOf("L" to -1, "R" to 1)
        fun parseInput(puzzle: PuzzleInputProvider): Pair<List<String>, List<String>> {
            val split = puzzle.get().split("\n\n")
            val board = split[0].split("\n")

            val directions = Regex("""(\d+|L|R)""").findAll(split[1]).toList().map { it.value }

            return board to directions
        }

        fun solve(
            puzzle: PuzzleInputProvider,
            lookAhead: (Triple<Int, Int, Int>, List<String>) -> Triple<Int, Int, Int>
        ): String {
            val (board, instructions) = parseInput(puzzle)

            // position is a triple of (x, y, direction)
            var position = Triple(board[0].indexOf("."), 0, 0)

            var lastTen = ArrayDeque<Triple<Int, Int, Int>>()
            lastTen.add(position)

            instructions.forEach {
                val moveCount = it.toIntOrNull()
                if (moveCount != null) {
                    // move "it" spaces
                    for (i in (0 until moveCount)) {
                        val nextPosition = lookAhead(position, board)
                        when (board[nextPosition.second][nextPosition.first]) {
                            '.' -> {
                                position = nextPosition
                                lastTen.add(position)
                                if (lastTen.size > 10) {
                                    lastTen.removeFirst()
                                }

//                                print(board, lastTen)
//                                sleep(500)
                            }
                            '#' -> break
                            else -> {
                                println(position)
                                throw IllegalStateException()
                            }
                        }
                    }
                } else {
                    position =
                        position.copy(third = (position.third + turn[it]!!).mod(directions.size))
                }
            }


            return ((position.second + 1) * 1000 + (position.first + 1) * 4 + position.third).toString()
        }

        private fun print(board: List<String>, lastTen: ArrayDeque<Triple<Int, Int, Int>>) {
            board.forEachIndexed { y, line ->
                line.forEachIndexed { x, c ->
                    val traversedPoint = lastTen.firstOrNull { it.first == x && it.second == y }
                    if (traversedPoint != null) {
                        print("\u001b[31m" + traversedPoint.third + "\u001b[0m")
                    } else {
                        print(c)
                    }
                }
                println()
            }
        }

        fun part1(puzzle: PuzzleInputProvider): String {
            return solve(puzzle, ::lookAhead2D)
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            return solve(puzzle, ::lookAhead3D)
        }
    }
}

fun lookAhead2D(start: Triple<Int, Int, Int>, board: List<String>): Triple<Int, Int, Int> {
    return when (start.third) {
        0 -> {
            var newX = (start.first + 1)
            if (newX == board[start.second].length) {
                newX = board[start.second].indexOfFirst { it != ' ' }
            }
            start.copy(first = newX)
        } // Right
        2 -> {
            var newX = (start.first - 1)
            if (newX < 0 || board[start.second][newX] == ' ') {
                newX = board[start.second].length - 1
            }
            start.copy(first = newX)
        } // Left
        1 -> {
            if (start.second < board.size - 1 && board[start.second + 1].length > start.first && board[start.second + 1][start.first] != ' ') {
                start.copy(second = start.second + 1)
            } else {
                var newY = 0
                while (board[newY].length < (start.first + 1) || board[newY][start.first] == ' ') {
                    newY++
                }

                start.copy(second = newY)
            }
        }
        3 -> {
            if (start.second > 0 && board[start.second - 1].length > start.first && board[start.second - 1][start.first] != ' ') {
                start.copy(second = start.second - 1)
            } else {
                var newY = board.size - 1
                while (board[newY].length < (start.first + 1) || board[newY][start.first] == ' ') {
                    newY--
                }

                start.copy(second = newY)
            }
        }
        else -> throw java.lang.IllegalStateException("Unexpected direction")
    }
}

/**
 * Interpreting the board as 6 squares with side length 50, imagine the board folded into a cube
 *   2 1
 *   3
 * 5 4
 * 6
 *
 * This function will determine if the current location is on an edge, facing "away" and reorient
 * the next position on the corresponding edge of the adjacent face. If not on an edge,
 * simply move forward
 */
fun lookAhead3D(start: Triple<Int, Int, Int>, board: List<String>): Triple<Int, Int, Int> {

    start.let { (x, y, d) ->
        when (d) {
            0 -> {
                return if (x == 149 && y in (0 until 50)) {
                    // right edge of 1, facing right. move to right edge of 4, facing left
                    Triple(99, 149 - y, 2)
                } else if (x == 99 && y in (50 until 100)) {
                    // right edge of 3, facing right. move to bottom edge of 1, facing up
                    Triple(y + 50, 49, 3)
                } else if (x == 99 && y in (100 until 150)) {
                    // right edge of 4, facing right. move to right edge of 1, facing left
                    Triple(149, 149 - y, 2)
                } else if (x == 49 && y in (150 until 200)) {
                    // right edge of 6, facing right. move to bottom edge of 4, facing up
                    Triple(y - 100, 149, 3)
                } else {
                    Triple(x + 1, y, d)
                }
            }
            1 -> {
                return if (x in (0 until 50) && y == 199) {
                    // bottom edge of 6, facing down. move to top edge of 1, facing down
                    Triple(x + 100, 0, 1)
                } else if (x in (50 until 100) && y == 149) {
                    // bottom edge of 4, facing down. move to right edge of 6, facing left
                    Triple(49, x + 100, 2)
                } else if (x in (100 until 150) && y == 49) {
                    // bottom edge of 1, facing down. move to right edge of 3, facing left
                    Triple(99, x - 50, 2)
                } else {
                    Triple(x, y + 1, d)
                }
            }
            2 -> {
                return if (x == 50 && y in (0 until 50)) {
                    // left edge of 2, facing left. move to left edge of 5, facing right
                    Triple(0, 149 - y, 0)
                } else if (x == 50 && y in (50 until 100)) {
                    // left edge of 3, facing left. move to top edge of 5, facing down
                    Triple(y - 50, 100, 1)
                } else if (x == 0 && y in (100 until 150)) {
                    // left edge of 5, facing left. move to left edge of 2, facing right
                    Triple(50, 149 - y, 0)
                } else if (x == 0 && y in (150 until 200)) {
                    // left edge of 6, facing left. move to top edge of 2, facing down
                    Triple(y - 100, 0, 1)
                } else {
                    Triple(x - 1, y, d)
                }
            }
            3 -> {
                return if (x in (0 until 50) && y == 100) {
                    // top edge of 5, facing up. move to left edge of 3, facing right
                    Triple(50, 50 + x, 0)
                } else if (x in (50 until 100) && y == 0) {
                    // top edge of 2, facing up. move to left edge of 6, facing right
                    Triple(0, x + 100, 0)
                } else if (x in (100 until 150) && y == 0) {
                    // top edge of 1, facing up. move to bottom edge of 6, facing up
                    Triple(x - 100, 199, 3)
                } else {
                    Triple(x, y - 1, d)
                }
            }
            else -> throw java.lang.IllegalStateException("Unexpected direction")
        }
    }
}

fun main() {

    val example = ExamplePuzzle(
        "        ...#\n" +
            "        .#..\n" +
            "        #...\n" +
            "        ....\n" +
            "...#.......#\n" +
            "........#...\n" +
            "..#....#....\n" +
            "..........#.\n" +
            "        ...#....\n" +
            "        .....#..\n" +
            "        .#......\n" +
            "        ......#.\n" +
            "\n" +
            "10R5L5R10L4R5L5"
    )

//    val p1 = Day22.part1(example)
//    println(p1)
//    assert(p1 == "6032")

//    Runner.solve(2022, 22, part1 = Day22::part1)
    Runner.solve(2022, 22, part2 = Day22::part2)
}

