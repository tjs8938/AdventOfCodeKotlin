package AdventOfCodeKotlin.puzzles.AoC2024

import AdventOfCodeKotlin.framework.ExamplePuzzle
import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner
import AdventOfCodeKotlin.util.Adjacent
import AdventOfCodeKotlin.util.Adjacent.Companion.buildRouteTable
import AdventOfCodeKotlin.util.Graph.Companion.buildGraph
import AdventOfCodeKotlin.util.Node

class Day10 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): Any {
            val input = puzzle.getAsGrid()
            val graph = buildGraph(input, ::Node)
            val routeTable = buildRouteTable(graph.values.filter { it.label == '0' }.toList(), { src, dest ->
                (src as Node).label.code + 1 == (dest as Node).label.code
            })

            val filteredRoutes = routeTable.mapValues { (_, value) -> value.filterKeys { (it as Node).label == '9' } }
            return filteredRoutes.values.map { it.size }.sum()
        }

        fun part2(puzzle: PuzzleInputProvider): Any {
            val input = puzzle.getAsGrid()
            val graph = buildGraph(input, ::Node)
            val condition = { src: Adjacent, dest: Adjacent ->
                (src as Node).label.code + 1 == (dest as Node).label.code
            }

            var toProcess: List<Adjacent> = graph.values.filter { it.label == '0' }.toList()

            repeat(9) {
                toProcess = toProcess.flatMap { start ->
                    start.neighbors.filter { n -> condition(start, n) }
                }
            }

            return toProcess.size
        }

        fun part1b(puzzle: PuzzleInputProvider): Any {
            val input = puzzle.getAsGrid()
            val graph = buildGraph(input, ::Node)
            val condition = { src: Adjacent, dest: Adjacent ->
                (src as Node).label.code + 1 == (dest as Node).label.code
            }

            var toProcess: List<Pair<Adjacent, Adjacent>> = graph.values.filter { it.label == '0' }.map { it to it }

            repeat(9) {
                toProcess = toProcess.flatMap { (trailhead, start) ->
                    start.neighbors.filter { n -> condition(start, n) }
                        .map { trailhead to it }
                }
            }

            return toProcess.toSet().size
        }
    }
}

fun main() {
    val ex1 = ExamplePuzzle(
        """
        89010123
        78121874
        87430965
        96549874
        45678903
        32019012
        01329801
        10456732
    """.trimIndent()
    )

    assert(Day10.part1(ex1) == 36)

    Runner.solve(2024, 10, part1 = Day10::part1b)

    Runner.solve(2024, 10, part2 = Day10::part2)
}