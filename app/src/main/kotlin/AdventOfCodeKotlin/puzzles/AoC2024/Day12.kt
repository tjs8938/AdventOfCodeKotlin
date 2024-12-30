package AdventOfCodeKotlin.puzzles.AoC2024

import AdventOfCodeKotlin.framework.ExamplePuzzle
import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner
import AdventOfCodeKotlin.util.Direction
import AdventOfCodeKotlin.util.Graph.Companion.buildGraph
import AdventOfCodeKotlin.util.Node

class Day12 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): Any {
            val regions = buildRegions(puzzle)

            return regions.sumOf { (type, plots) -> plots.size.toLong() * plots.sumOf { 4 - it.neighbors.count { (it as Node).label == type } } }
        }

        private fun buildRegions(
            puzzle: PuzzleInputProvider
        ): MutableList<Pair<Char, MutableSet<Node>>> {
            val regions: MutableList<Pair<Char, MutableSet<Node>>> = mutableListOf()
            val input = puzzle.getAsGrid()
            val graph = buildGraph(input, ::Node)
            val remainingPlots = graph.values.toMutableList()

            while (remainingPlots.isNotEmpty()) {
                val first = remainingPlots.removeFirst()
                var toProcess = setOf(first)
                val type = first.label
                val region = mutableSetOf(first)

                while (toProcess.isNotEmpty()) {
                    toProcess = toProcess.flatMap {
                        it.neighbors.map { it as Node }
                            .filter { n -> n.label == type && n in remainingPlots }
                    }.toSet()

                    remainingPlots.removeAll(toProcess)
                    region.addAll(toProcess)
                }

                regions.add(type to region)
            }

            return regions
        }

        fun part2(puzzle: PuzzleInputProvider): Any {
            val regions = buildRegions(puzzle)

            return regions.sumOf { (type, plots) ->
                val area = plots.size.toLong()
                var perimeter = 0L

                val xRange = plots.minOf { it.x }..plots.maxOf { it.x }
                val yRange = plots.minOf { it.y }..plots.maxOf { it.y }

                for (x in xRange) {
                    val leftEdge = plots.filter { it.x == x && plots.none { n -> n.y == it.y && n.x == it.x - 1 } }
                    val rightEdge = plots.filter { it.x == x && plots.none { n -> n.y == it.y && n.x == it.x + 1 } }

                    perimeter += leftEdge.takeIf { it.isNotEmpty() }?.let { edge -> edge.sortedBy { it.y }.zipWithNext().count { (a, b) -> a.y + 1 != b.y } + 1 } ?: 0
                    perimeter += rightEdge.takeIf { it.isNotEmpty() }?.let { edge -> edge.sortedBy { it.y }.zipWithNext().count { (a, b) -> a.y + 1 != b.y } + 1 } ?: 0
                }

                for (y in yRange) {
                    val topEdge = plots.filter { it.y == y && plots.none { n -> n.x == it.x && n.y == it.y - 1 } }
                    val bottomEdge = plots.filter { it.y == y && plots.none { n -> n.x == it.x && n.y == it.y + 1 } }

                    perimeter += topEdge.takeIf { it.isNotEmpty() }?.let { edge -> edge.sortedBy { it.x }.zipWithNext().count { (a, b) -> a.x + 1 != b.x } + 1 } ?: 0
                    perimeter += bottomEdge.takeIf { it.isNotEmpty() }?.let { edge -> edge.sortedBy { it.x }.zipWithNext().count { (a, b) -> a.x + 1 != b.x } + 1 } ?: 0
                }

                area * perimeter
            }
        }
    }
}

fun main() {

    val ex1 = ExamplePuzzle("""
        AAAA
        BBCD
        BBCC
        EEEC
    """.trimIndent())

    val ex2 = ExamplePuzzle("""
        RRRRIICCFF
        RRRRIICCCF
        VVRRRCCFFF
        VVRCCCJFFF
        VVVVCJJCFE
        VVIVCCJJEE
        VVIIICJJEE
        MIIIIIJJEE
        MIIISIJEEE
        MMMISSJEEE
    """.trimIndent())
//    Day12.part1(ex1).let {
//        println(it)
//        assert(it == 140L)
//    }
//
//    Day12.part1(ex2).let {
//        println(it)
//        assert(it == 1930L)
//    }
//
//    Runner.solve(2024, 12, part1 = Day12::part1)

    Day12.part2(ex1).let {
        println(it)
        assert(it == 80L)
    }

    Day12.part2(ex2).let {
        println(it)
        assert(it == 1206L)
    }

    Runner.solve(2024, 12, part2 = Day12::part2)
}