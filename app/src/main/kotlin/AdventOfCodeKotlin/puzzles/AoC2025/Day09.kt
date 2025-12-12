package AdventOfCodeKotlin.puzzles.AoC2025

import AdventOfCodeKotlin.oldframework.ExamplePuzzle
import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner
import AdventOfCodeKotlin.util.Combinatorics
import AdventOfCodeKotlin.util.Line
import AdventOfCodeKotlin.util.MemoizedFunction.Companion.memoize
import AdventOfCodeKotlin.util.Point2d
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Day09 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {
            val points = puzzle.getAsString().map { it.split(",").map { it.toLong() }.let { Point2d(it[0], it[1]) } }
            val opposites = Combinatorics.combinations(points, 2).map { Line(it[0], it[1]) }
            return opposites.maxOf { (abs(it.p1.x - it.p2.x) + 1) * (abs(it.p1.y - it.p2.y) + 1) }.toString()
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            val points = puzzle.getAsString().map { it.split(",").map { it.toLong() }.let { Point2d(it[0], it[1]) } }
            val opposites = Combinatorics.combinations(points, 2).map { Line(it[0], it[1]) }
            val walls = points.zipWithNext().toMutableList().let {
                it.add(Pair(points.last(), points.first()))
                it
            }.map { Line(it.first, it.second) }
            val groupedWalls = walls.groupBy { it.isHorizontal() }

            val insideMemo = memoize { point: Point2d ->
                inside(point, groupedWalls[false]!!, groupedWalls[true]!!)
            }

            fun insideRectangle(c1: Point2d, c2: Point2d): Boolean {
                for (x in c1.x..c2.x) {
                    if (!insideMemo(Point2d(x, c1.y))) {
                        return false
                    }
                    if (!insideMemo(Point2d(x, c2.y))) {
                        return false
                    }
                }
                for (y in c1.y..c2.y) {
                    if (!insideMemo(Point2d(c1.x, y))) {
                        return false
                    }
                    if (!insideMemo(Point2d(c2.x, y))) {
                        return false
                    }
                }
                return true
            }

            return opposites
                .sortedWith(compareBy<Line> { (abs(it.p1.x - it.p2.x) + 1) * (abs(it.p1.y - it.p2.y) + 1) }.reversed())
                .first { (c1, c2) ->
                    val upperLeft = Point2d(min(c1.x, c2.x), min(c1.y, c2.y))
                    val lowerRight = Point2d(max(c1.x, c2.x), max(c1.y, c2.y))
                    insideRectangle(upperLeft, lowerRight)
                }
                .let { (abs(it.p1.x - it.p2.x) + 1) * (abs(it.p1.y - it.p2.y) + 1) }.toString()
        }

        fun wallCount(walls: List<Line>, horizontal: Boolean): Int {
            return if (horizontal) {
                walls.size - Combinatorics.combinations(walls, 2)
                    .count { it[0].minX == it[1].maxX || it[0].maxX == it[1].minX }
            } else {
                walls.size - Combinatorics.combinations(walls, 2)
                    .count { it[0].minY == it[1].maxY || it[0].maxY == it[1].minY }
            }
        }

        fun inside(point: Point2d, vWalls: List<Line>, hWalls: List<Line>): Boolean {
            if (vWalls.any { it.p1.x == point.x && point.y in it.minY..it.maxY }) {
                return true
            }
            if (hWalls.any { it.p1.y == point.y && point.x in it.minX..it.maxX }) {
                return true
            }
            val wallCounts = listOf(
                vWalls.filter { it.p1.x < point.x && point.y in it.minY..it.maxY } to false, // left
                vWalls.filter { it.p1.x > point.x && point.y in it.minY..it.maxY } to false, // right
                hWalls.filter { it.p1.y < point.y && point.x in it.minX..it.maxX } to true, // above
                hWalls.filter { it.p1.y > point.y && point.x in it.minX..it.maxX } to true  // below
            ).map { wallCount(it.first, it.second) }

            // counts of walls in all directions must be odd to be inside
            return wallCounts.all { it % 2 == 1 }
        }
    }
}

fun main() {
    Runner.solve(2025, 9, 1, Day09::part1)

    val ex = ExamplePuzzle(
        """
        7,1
        11,1
        11,7
        9,7
        9,5
        2,5
        2,3
        7,3
    """.trimIndent(), "24"
    )
    val ex2 = ExamplePuzzle(
        """
        1,1
        8,1
        8,6
        6,6
        6,3
        3,3
        3,8
        8,8
        8,13
        1,13
    """.trimIndent(), "48"
    )
    Runner.solve(2025, 9, 2, Day09::part2, ex, ex2)
}

