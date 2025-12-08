package AdventOfCodeKotlin.puzzles.AoC2024

import AdventOfCodeKotlin.oldframework.ExamplePuzzle
import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner
import AdventOfCodeKotlin.puzzles.AoC2022.plus
import AdventOfCodeKotlin.util.Graph.Companion.buildGraph
import AdventOfCodeKotlin.util.Node

class Day08 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): Any {
            val input = puzzle.getAsGrid()
            val graph = buildGraph(input, ::Node)

            val antennas = graph.values.filter { it.value != '.' }.groupBy { it.value }

            var antinodes = mutableSetOf<Pair<Int, Int>>()
            antennas.forEach { (value, nodes) ->
                nodes.forEachIndexed { a_index, a ->
                    nodes.forEachIndexed { b_index, b ->
                        if (a_index != b_index) {
                            val antinodePos = (2*a.y - b.y) to (2*a.x - b.x)
                            if (graph.containsKey(antinodePos)) {
                                antinodes.add(antinodePos)
                            }
                        }
                    }
                }
            }

            return antinodes.size
        }

        fun part2(puzzle: PuzzleInputProvider): Any {
            val input = puzzle.getAsGrid()
            val graph = buildGraph(input, ::Node)

            val antennas = graph.values.filter { it.value != '.' }.groupBy { it.value }

            var antinodes = mutableSetOf<Pair<Int, Int>>()
            antennas.forEach { (value, nodes) ->
                nodes.forEachIndexed { a_index, a ->
                    nodes.forEachIndexed { b_index, b ->
                        if (a_index != b_index) {
                            val antinodeDiff = (a.y - b.y) to (a.x - b.x)
                            var antinodePos = a.y to a.x
                            while (graph.containsKey(antinodePos)) {
                                antinodes.add(antinodePos)
                                antinodePos = antinodeDiff + antinodePos
                            }
                        }
                    }
                }
            }

            return antinodes.size
        }
    }
}

fun main() {

    val ex1 = ExamplePuzzle("""
        ............
        ........0...
        .....0......
        .......0....
        ....0.......
        ......A.....
        ............
        ............
        ........A...
        .........A..
        ............
        ............
    """.trimIndent())

    assert(Day08.part1(ex1) == 14)
    assert(Day08.part2(ex1) == 34)

//    Runner.solve(2024, 8, part1 = Day08::part1)

    Runner.solve(2024, 8, part2 = Day08::part2)
}