package AdventOfCodeKotlin.puzzles.AoC2025

import AdventOfCodeKotlin.oldframework.ExamplePuzzle
import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner
import AdventOfCodeKotlin.util.MemoizedFunction.Companion.memoize
import AdventOfCodeKotlin.util.Point2d

class Day12 {
    companion object {

        class Region(val width: Int, val height: Int, val presentCounts: List<Int>) {}

        class Present(val id: Int, val shape: Set<Point2d>) {

            val width: Long
                get() = shape.maxOf { it.x } + 1
            val height: Long
                get() = shape.maxOf { it.y } + 1

            fun rotate(): Present {
                val newShape = shape.map { Point2d(-it.y + height - 1, it.x) }.toSet()
                return Present(id, newShape)
            }

            fun flip(): Present {
                val newShape = shape.map { Point2d(it.x, -it.y + height - 1) }.toSet()
                return Present(id, newShape)
            }

            fun translate(offset: Point2d): Present {
                val newShape = shape.map { it + offset }.toSet()
                return Present(id, newShape)
            }

            fun allOrientations(): Set<Present> {
                val orientations = mutableSetOf<Present>()
                var current = this
                repeat(4) {
                    orientations.add(current)
                    orientations.add(current.flip())
                    current = current.rotate()
                }
                return orientations
            }

            override fun toString(): String {
                val strBuilder = StringBuilder("")
                (0 until height).forEach { y ->
                    (0 until width).forEach { x ->
                        if (Point2d(x, y) in shape) {
                            strBuilder.append("#")
                        } else {
                            strBuilder.append(".")
                        }
                    }
                    strBuilder.append("\n")
                }
                return strBuilder.toString()
            }

            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as Present

                if (id != other.id) return false
                if (shape != other.shape) return false

                return true
            }

            override fun hashCode(): Int {
                var result = id
                result = 31 * result + shape.hashCode()
                return result
            }


        }

        fun part1(puzzle: PuzzleInputProvider): String {
            val inputBlocks = puzzle.get().split("\n\n")
            val regionStrings = inputBlocks.last().lines()
            val regions = regionStrings.map {
                val parts = it.split(": ")
                val dims = parts[0].split("x").map { it.toInt() }
                val counts = parts[1].split(" ").map { it.toInt() }
                Region(dims[0], dims[1], counts)
            }

            val presents = inputBlocks.dropLast(1).map { block ->
                val lines = block.lines()
                val id = lines[0].dropLast(1).toInt()
                val shape = mutableSetOf<Point2d>()
                lines.drop(1).forEachIndexed { y, line ->
                    line.forEachIndexed { x, c ->
                        if (c == '#') {
                            shape.add(Point2d(x.toLong(), y.toLong()))
                        }
                    }
                }
                Present(id, shape)
            }

            val testRegion = memoize<Pair<Set<Point2d>, List<Int>>, Boolean> { (availablePoints, presentReqs) ->
                if (presentReqs.all { count -> count == 0 }) {
                    true
                } else {
                    val possiblePresents = presentReqs.mapIndexed { x, count -> x to count }
                        .filter { it.second > 0 }
                        .map { it.first }
                        .map { presentIndex ->
                            presentIndex to presents[presentIndex].allOrientations()
                        }.flatMap { it.second.map { p -> it.first to p} }
                    for (point in availablePoints) {
                        for (presentPair in possiblePresents) {
                            val (presentIndex, present) = presentPair
                            val translatedPresent = present.translate(point)
                            if (translatedPresent.shape.all { it in availablePoints }) {
                                val newAvailablePoints = availablePoints - translatedPresent.shape
                                val newPresentReqs = presentReqs.toMutableList()
                                newPresentReqs[presentIndex] = newPresentReqs[presentIndex] - 1
                                if (this(newAvailablePoints to newPresentReqs)) {
                                    return@memoize true
                                }
                            }
                        }
                    }
                    false
                }
            }
            var count = 0
            for (region in regions) {
                val availablePoints = mutableSetOf<Point2d>()
                for (y in 0 until region.height) {
                    for (x in 0 until region.width) {
                        availablePoints.add(Point2d(x.toLong(), y.toLong()))
                    }
                }
                if (testRegion(availablePoints to region.presentCounts)) {
                    count++
                }
            }

            return count.toString()
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            // TODO: Implement part 2 logic
            return ""
        }
    }
}

fun main() {
    val ex = ExamplePuzzle(
        """
        0:
        ###
        ##.
        ##.

        1:
        ###
        ##.
        .##

        2:
        .##
        ###
        ##.

        3:
        ##.
        ###
        ##.

        4:
        ###
        #..
        ###

        5:
        ###
        .#.
        ###

        4x4: 0 0 0 0 2 0
        12x5: 1 0 1 0 2 2
        12x5: 1 0 1 0 3 2
    """.trimIndent(), "2"
    )
    Runner.solve(2025, 12, 1, Day12::part1, ex)
    Runner.solve(2025, 12, 2, Day12::part2)
}

