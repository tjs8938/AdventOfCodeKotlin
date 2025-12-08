package AdventOfCodeKotlin.puzzles.AoC2023

import AdventOfCodeKotlin.oldframework.ExamplePuzzle
import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner


class Day22 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {

            val bricks = settleBricks(puzzle)

//            bricks.sortedByDescending { it.minHeight }.forEach { println(it) }

            return bricks.count { brick -> brick.supporting.size == 0 ||
            brick.supporting.all { supported -> supported.supportedBy.size > 1 }}.toString()
        }

        private fun settleBricks(puzzle: PuzzleInputProvider): List<Brick> {
            val bricks = puzzle.getAsString().map { Brick(it) }.sortedBy { it.minHeight }
            val topOfThePile = bricks.maxOf { it.minHeight }

            (2..topOfThePile).forEach { height ->
                val fallingBricks = bricks.filter { it.minHeight == height }
                fallingBricks.forEach { fallingBrick ->
                    var supportingBricks: List<Brick>
                    do {
                        supportingBricks = bricks.filter {
                            it.maxHeight + 1 == fallingBrick.minHeight &&
                                it.points.intersect(fallingBrick.points).isNotEmpty()
                        }
                        if (supportingBricks.isEmpty()) {
                            fallingBrick.minHeight--
                            fallingBrick.maxHeight--
                        }
                    } while (supportingBricks.isEmpty() && fallingBrick.minHeight > 1)
                    supportingBricks.forEach {
                        it.supporting.add(fallingBrick)
                        fallingBrick.supportedBy.add(it)
                    }
                }
            }
            return bricks
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            val bricks = settleBricks(puzzle)

            return bricks.sumOf { it.fallCount() }.toString()
        }

    }

    class Brick(s: String) {
        var minHeight: Int
        var maxHeight: Int
        val points: MutableSet<Pair<Int, Int>>
        val supporting = mutableListOf<Brick>()
        val supportedBy = mutableListOf<Brick>()

        init {
            val regex = Regex("""([0-9]+)""")
            val inputs = regex.findAll(s).map { it.value.toInt() }.toList()
            minHeight = inputs[2]
            maxHeight = inputs[5]
            points = mutableSetOf()
            (inputs[0] .. inputs[3]).forEach { x ->
                (inputs[1] .. inputs[4]).forEach { y ->
                    points.add(x to y)
                }
            }
        }

        override fun toString(): String {
            return "Brick(minHeight=$minHeight, maxHeight=$maxHeight, points=$points, supporting=${supporting.size})"
        }

        fun fallCount() : Int {
            val disintegrating = mutableSetOf(this)
            do {

                val allSupportedBy = disintegrating.flatMap { it.supporting }
                    .filterNot { disintegrating.contains(it) }
                    .filter { it.supportedBy.minus(disintegrating).isEmpty() }.toSet()
                disintegrating.addAll(allSupportedBy)
            } while (allSupportedBy.isNotEmpty())
            return disintegrating.size - 1
        }

    }
}


fun main() {
    val ex1 = ExamplePuzzle("""
        1,0,1~1,2,1
        0,0,2~2,0,2
        0,2,3~2,2,3
        0,0,4~0,2,4
        2,0,5~2,2,5
        0,1,6~2,1,6
        1,1,8~1,1,9
    """.trimIndent())

    Day22.part1(ex1).let {
        println(it)
        assert(it == "5")
    }

    Runner.solve(2023, 22, part1 = Day22::part1)

    Day22.part2(ex1).let {
        println(it)
        assert(it == "7")
    }
    Runner.solve(2023, 22, part2 = Day22::part2)
}