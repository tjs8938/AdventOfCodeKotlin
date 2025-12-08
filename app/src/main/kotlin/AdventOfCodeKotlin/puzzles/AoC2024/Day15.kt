package AdventOfCodeKotlin.puzzles.AoC2024

import AdventOfCodeKotlin.oldframework.ExamplePuzzle
import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner
import AdventOfCodeKotlin.util.Direction

class Day15 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): Any {
            val (mapInput, moveInput) = puzzle.get().split("\n\n")
            val map = mapInput.lines().map { it.toMutableList() }.toMutableList()

            val initY = map.indexOfFirst { '@' in it }
            val initX = map[initY].indexOf('@')

            fun tryMove(position: Pair<Int, Int>, dir: Direction): Boolean {
                val nextPos = dir + position
                val nextValue = map[nextPos.first][nextPos.second]
                if (nextValue == '.' || (nextValue == 'O' && tryMove(nextPos, dir))) {
                    val current = map[position.first][position.second]
                    map[position.first][position.second] = map[nextPos.first][nextPos.second]
                    map[nextPos.first][nextPos.second] = current

                    return true
                }
                return false
            }

            var position = initY to initX
            moveInput.filterNot { it == '\n' }.forEach { move ->
                val dir = Direction.fromChar(move)
                if (tryMove(position, dir)) {
                    position = dir + position
                }
            }


            return map.mapIndexed { y, chars ->
                chars.mapIndexed { x, c ->
                    if (c == 'O') {
                        y * 100 + x
                    } else {
                        0
                    }
                }.sum()
            }.sum()
        }

        fun part2(puzzle: PuzzleInputProvider): Any {
            val (mapInput, moveInput) = puzzle.get().split("\n\n")
            val map = mapInput.lines().map { it.flatMap { c ->
                when(c) {
                    '#' -> listOf('#', '#')
                    '.' -> listOf('.', '.')
                    '@' -> listOf('@', '.')
                    'O' -> listOf('[', ']')
                    else -> listOf(c)
                }
            }.toMutableList() }.toMutableList()

//            map.forEach { println(it.joinToString("")) }

            val initY = map.indexOfFirst { '@' in it }
            val initX = map[initY].indexOf('@')

            fun moveHorizontal(position: Pair<Int, Int>, dir: Direction): Boolean {

                val nextPos = dir + position
                val nextValue = map[nextPos.first][nextPos.second]
                if (nextValue == '.' || (nextValue in listOf('[', ']') && moveHorizontal(nextPos, dir))) {
                    val current = map[position.first][position.second]
                    map[position.first][position.second] = map[nextPos.first][nextPos.second]
                    map[nextPos.first][nextPos.second] = current

                    return true
                }
                return false
            }

            fun tryMoveVertical(position: Pair<Int, Int>, dir: Direction): Boolean {
                val nextPos = dir + position
                val nextValue = map[nextPos.first][nextPos.second]
                return when(nextValue) {
                    '.' -> true
                    '[' -> tryMoveVertical(nextPos, dir) && tryMoveVertical(Direction.EAST + nextPos, dir)
                    ']' -> tryMoveVertical(nextPos, dir) && tryMoveVertical(Direction.WEST + nextPos, dir)
                    else -> false
                }
            }

            fun moveVertical(position: Pair<Int, Int>, dir: Direction): Boolean {
                if (tryMoveVertical(position, dir)) {
                    val nextPos = dir + position
                    val nextValue = map[nextPos.first][nextPos.second]
                    if (nextValue == '[') {
                        moveVertical(nextPos, dir)
                        moveVertical(Direction.EAST + nextPos, dir)
                    } else if (nextValue == ']') {
                        moveVertical(nextPos, dir)
                        moveVertical(Direction.WEST + nextPos, dir)
                    }

                    val current = map[position.first][position.second]
                    map[position.first][position.second] = map[nextPos.first][nextPos.second]
                    map[nextPos.first][nextPos.second] = current

                    return true
                } else {
                    return false
                }
            }


            var position = initY to initX
            moveInput.filterNot { it == '\n' }.forEach { c ->
                val dir = Direction.fromChar(c)
                if (dir == Direction.NORTH || dir == Direction.SOUTH) {
                    if (moveVertical(position, dir)) {
                        position = dir + position
                    }
                } else {
                    if (moveHorizontal(position, dir)) {
                        position = dir + position
                    }
                }
            }


            return map.mapIndexed { y, chars ->
                chars.mapIndexed { x, c ->
                    if (c == '[') {
                        y * 100 + x
                    } else {
                        0
                    }
                }.sum()
            }.sum()
        }
    }
}

fun main() {

    val ex1 = ExamplePuzzle("""
        ########
        #..O.O.#
        ##@.O..#
        #...O..#
        #.#.O..#
        #...O..#
        #......#
        ########

        <^^>>>vv<v>>v<<
    """.trimIndent())

//    Day15.part1(ex1).let {
//        println(it)
//        if(it != 2028) throw AssertionError()
//    }
//    Runner.solve(2024, 15, part1 = Day15::part1)

    val ex2 = ExamplePuzzle("""
        ##########
        #..O..O.O#
        #......O.#
        #.OO..O.O#
        #..O@..O.#
        #O#..O...#
        #O..O..O.#
        #.OO.O.OO#
        #....O...#
        ##########

        <vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^
        vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v
        ><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<
        <<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^
        ^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><
        ^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^
        >^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^
        <><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>
        ^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>
        v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^
    """.trimIndent())

    Day15.part2(ex2).let {
        println(it)
        if(it != 9021) throw AssertionError()
    }

    Runner.solve(2024, 15, part2 = Day15::part2)
}