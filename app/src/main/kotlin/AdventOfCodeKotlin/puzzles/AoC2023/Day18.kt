package AdventOfCodeKotlin.puzzles.AoC2023

import AdventOfCodeKotlin.framework.ExamplePuzzle
import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner
import AdventOfCodeKotlin.puzzles.AoC2023.Day10.Directions.NORTH
import AdventOfCodeKotlin.puzzles.AoC2023.Day10.Directions.SOUTH
import AdventOfCodeKotlin.puzzles.AoC2023.Day10.Directions.EAST
import AdventOfCodeKotlin.puzzles.AoC2023.Day10.Directions.WEST


class Day18 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {

            var current = 0 to 0
            val trench = mutableSetOf(0 to 0)
            val regex = Regex("""(.) ([0-9]+) (.*)""")
            puzzle.getAsString().forEach {
                regex.matchEntire(it)!!
                    .destructured
                    .let { (dir, count, color) ->
                        repeat(count.toInt()) {
                            current = dirMap[dir]!!.plus(current)
                            trench.add(current)
                        }
                    }
            }

//            debug(trench)
            val northEdge = trench.minOf { it.first }
            var inside = trench.filter { it.first == northEdge }.map { SOUTH.plus(it) }
                .filterNot { trench.contains(it) }.toMutableSet()

            do {
                // lazy BFS to fill in any holes in the "inside sections"
                val previousInside = inside
                inside = inside.flatMap { listOf(it, NORTH.plus(it), SOUTH.plus(it), EAST.plus(it), WEST.plus(it)) }
                    .filterNot { trench.contains(it) }
                    .toMutableSet()

            } while (previousInside.size != inside.size)

            trench.addAll(inside)
            return trench.size.toString()
        }

        val dirMap = mapOf("R" to EAST, "D" to SOUTH, "L" to WEST, "U" to NORTH)
        val intDirMap = mapOf("0" to EAST, "1" to SOUTH, "2" to WEST, "3" to NORTH)


        fun part1b(puzzle: PuzzleInputProvider): String {
            var current = 0 to 0
            val regex = Regex("""(.) ([0-9]+) (.*)""")
            val horizontalEdges = mutableListOf<Edge>()
            val verticalEdges = mutableListOf<Edge>()

            puzzle.getAsString().forEach {
                regex.matchEntire(it)!!
                    .destructured
                    .let { (dir, countStr, color) ->
                        val count = countStr.toInt()
                        val nextSpace = dirMap[dir]!!.move(current, count)
                        when(dir) {
                            "R" -> horizontalEdges.add(Edge(current, nextSpace))
                            "D" -> verticalEdges.add(Edge(current, nextSpace))
                            "L" -> horizontalEdges.add(Edge(nextSpace, current))
                            "U" -> verticalEdges.add(Edge(nextSpace, current))
                        }
                        current = nextSpace
                    }
            }

            return countSpace(horizontalEdges, verticalEdges)
        }
        fun part2(puzzle: PuzzleInputProvider): String {
            var current = 0 to 0
            val regex = Regex(""".*#(.*)(.).""")

            val horizontalEdges = mutableListOf<Edge>()
            val verticalEdges = mutableListOf<Edge>()
            puzzle.getAsString().forEach {
                regex.matchEntire(it)!!
                    .destructured
                    .let { (hexCount, dir) ->
                        val count = hexCount.toInt(16)
                        val nextSpace = intDirMap[dir]!!.move(current, count)
                        when(dir) {
                            "0" -> horizontalEdges.add(Edge(current, nextSpace))
                            "1" -> verticalEdges.add(Edge(current, nextSpace))
                            "2" -> horizontalEdges.add(Edge(nextSpace, current))
                            "3" -> verticalEdges.add(Edge(nextSpace, current))
                        }
                        current = nextSpace
                    }
            }

            return countSpace(horizontalEdges, verticalEdges)
        }

        private fun countSpace(
            horizontalEdges: MutableList<Edge>,
            verticalEdges: MutableList<Edge>
        ): String {
            horizontalEdges.sortBy { it.upperLeft.first }
            verticalEdges.sortBy { it.upperLeft.second }

            val northEdge = horizontalEdges.first().upperLeft.first
            val southEdge = horizontalEdges.last().lowerRight.first

            var trenchSize = 0L
            trenchSize += horizontalEdges.sumOf { it.lowerRight.second - it.upperLeft.second}
            trenchSize += verticalEdges.sumOf { it.lowerRight.first - it.upperLeft.first}
            (northEdge..southEdge).forEach { rowIndex ->
                val currentVerticals =
                    verticalEdges.filter { rowIndex in (it.upperLeft.first..it.lowerRight.first) }
                var insideLoop = false
                pairs(currentVerticals).forEach { (left, right) ->
                    val tempEdge =
                        Edge(rowIndex to left.upperLeft.second, rowIndex to right.upperLeft.second)
                    if (horizontalEdges.contains(tempEdge)) {
                        if (left.upperLeft.first == right.upperLeft.first ||
                            left.lowerRight.first == right.lowerRight.first
                        ) {
                            insideLoop = !insideLoop
                        }
                    } else {
                        insideLoop = !insideLoop
                        if (insideLoop) {
                            trenchSize += (right.upperLeft.second - left.upperLeft.second - 1)
                        }
                    }
                }
            }

            return trenchSize.toString()
        }


    }

    data class Edge(val upperLeft: Pair<Int, Int>, val lowerRight: Pair<Int, Int>) {

    }
}

fun <T> pairs(items: List<T>): List<Pair<T, T>> {
    return items.subList(0, items.size - 1).zip(items.subList(1, items.size))
}


fun main() {
    val ex1 = ExamplePuzzle(
        """
        R 6 (#70c710)
        D 5 (#0dc571)
        L 2 (#5713f0)
        D 2 (#d2c081)
        R 2 (#59c680)
        D 2 (#411b91)
        L 5 (#8ceee2)
        U 2 (#caa173)
        L 1 (#1b58a2)
        U 2 (#caa171)
        R 2 (#7807d2)
        U 3 (#a77fa3)
        L 2 (#015232)
        U 2 (#7a21e3)
    """.trimIndent()
    )

    assert(Day18.part1b(ex1) == "62")
//    Runner.solve(2023, 18, part1 = Day18::part1)
    assert(Day18.part2(ex1) == "952408144115")
    Runner.solve(2023, 18, part2 = Day18::part2)
}