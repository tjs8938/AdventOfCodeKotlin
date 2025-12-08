package AdventOfCodeKotlin.puzzles.AoC2024

import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner

class Day14 {
    companion object {

        class Robot(var position: Pair<Int, Int>, val velocity: Pair<Int, Int>, val width: Int, val height: Int) {
            fun move(count: Int) {
                position = (position.first + velocity.first * count).mod(width) to
                        (position.second + velocity.second * count).mod(height)
            }

            override fun toString(): String {
                return "Robot(position=$position, velocity=$velocity)"
            }
        }
        fun part1(puzzle: PuzzleInputProvider, width: Int, height: Int): Any {
            val robots = puzzle.getAsString().map {
                val (pX, pY, vX, vY) = Regex("""p=(.+),(.+) v=(.+),(.+)""").find(it)!!.destructured
                Robot(pX.toInt() to pY.toInt(), vX.toInt() to vY.toInt(), width, height)
            }

            robots.forEach { robot ->
                robot.move(100)
            }

            val midX = width / 2
            val midY = height / 2
            var safety = 1

            safety *= robots.count { it.position.first < midX && it.position.second < midY }
            safety *= robots.count { it.position.first < midX && it.position.second > midY }
            safety *= robots.count { it.position.first > midX && it.position.second < midY }
            safety *= robots.count { it.position.first > midX && it.position.second > midY }

            return safety
        }

        fun part2(puzzle: PuzzleInputProvider, width: Int, height: Int): Any {
            val robots = puzzle.getAsString().map {
                val (pX, pY, vX, vY) = Regex("""p=(.+),(.+) v=(.+),(.+)""").find(it)!!.destructured
                Robot(pX.toInt() to pY.toInt(), vX.toInt() to vY.toInt(), width, height)
            }

            var movement = 1
            var count = 1
            while (true) {
                robots.forEach { robot ->
                    robot.move(movement)
                }
                if ((0 until height).all { y ->
                    robots.filter{ it.position.second == y }
                        .sortedBy { it.position.first }
                        .zipWithNext()
                        .count { it.second.position.first - it.first.position.first > 1 } < 7 }) {
                    println("${count}----------------------------------------------------------------------------------------------------------------------------------------------------------------")
                    (0 until height).forEach { y ->
                        (0 until width).forEach { x ->
                            if (robots.any { it.position == x to y }) {
                                print("#")
                            } else {
                                print(".")
                            }
                        }
                        println()
                    }
                    val input = readln()
                    if (input.isNotBlank()) {
                        return count
                    }
                }
                count ++
            }
            return ""
        }
    }
}

fun main() {

//    val ex1 = ExamplePuzzle("""
//        p=0,4 v=3,-3
//        p=6,3 v=-1,-3
//        p=10,3 v=-1,2
//        p=2,0 v=2,-1
//        p=0,0 v=1,3
//        p=3,0 v=-2,-2
//        p=7,6 v=-1,-3
//        p=3,0 v=-1,-2
//        p=9,3 v=2,3
//        p=7,3 v=-1,2
//        p=2,4 v=2,-3
//        p=9,5 v=-3,-3
//    """.trimIndent())
//    Day14.part1(ex1, 11, 7).let {
//        println(it)
//        if (it != 12) throw AssertionError("Test failed")
//    }

//     Runner.solve(2024, 14, part1 = { puz -> Day14.part1(puz, 101, 103) })

     Runner.solve(2024, 14, part2 = { puz -> Day14.part2(puz, 101, 103) })
}