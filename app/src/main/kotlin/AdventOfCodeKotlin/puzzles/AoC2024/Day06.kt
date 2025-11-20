package AdventOfCodeKotlin.puzzles.AoC2024

import AdventOfCodeKotlin.framework.ExamplePuzzle
import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner
import AdventOfCodeKotlin.util.Direction
import AdventOfCodeKotlin.util.Mover
import AdventOfCodeKotlin.util.Node
import AdventOfCodeKotlin.util.Graph.Companion.buildGraph

class Day06 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): Any {
            val input = puzzle.getAsString().map { it.toList() }

            var start: Node<Char>? = null
            val graph = buildGraph(input, ::Node, { it != '#' }, mapOf('^' to { _, node -> start = node }))
            val guard = Mover(start!!.y to start!!.x, Direction.NORTH, graph)

            val visited = mutableSetOf(guard.loc)
            while (true) {
                if (guard.canMoveForward()) {
                    guard.moveForward()
                    visited.add(guard.loc)
                } else if (guard.isAtEdge()) {
                    break
                } else {
                    guard.turnRight()
                }
            }

            return visited.size
        }

        fun part2(puzzle: PuzzleInputProvider): Any {
            val input = puzzle.getAsString().map { it.toList() }

            var start: Node<Char>? = null
            val graph = buildGraph(input, ::Node, { it != '#' }, mapOf('^' to { _, node -> start = node }))

            val unblockedGuard = Mover(start!!.y to start!!.x, Direction.NORTH, graph)

            val unblockedVisited = mutableSetOf(unblockedGuard.loc)
            while (true) {
                if (unblockedGuard.canMoveForward()) {
                    unblockedGuard.moveForward()
                    unblockedVisited.add(unblockedGuard.loc)
                } else if (unblockedGuard.isAtEdge()) {
                    break
                } else {
                    unblockedGuard.turnRight()
                }
            }

            var loopCount = 0
            val allKeys = unblockedVisited.filter { it != start!!.y to start!!.x }
            allKeys.forEach { location ->

                val guard = Mover(start!!.y to start!!.x, Direction.NORTH, graph)

                val temp = graph.remove(location)!!

                val visited = mutableSetOf(guard.loc to guard.dir)
                while (true) {
                    if (guard.canMoveForward()) {
                        guard.moveForward()
                        val locDir = (guard.loc to guard.dir)
                        if (locDir in visited) {
                            loopCount++
                            break
                        }
                        visited.add(guard.loc to guard.dir)
                    } else if (guard.isAtEdge()) {
                        break
                    } else {
                        guard.turnRight()
                    }
                }

                graph[location] = temp
            }

            return loopCount
        }
    }
}

fun main() {
    val example = ExamplePuzzle(
        """
        ....#.....
        .........#
        ..........
        ..#.......
        .......#..
        ..........
        .#..^.....
        ........#.
        #.........
        ......#...
    """.trimIndent()
    )
    assert(Day06.part1(example) == 41)
    assert(Day06.part2(example) == 6)
//    Runner.solve(2024, 6, part1 = Day06::part1)
    Runner.solve(2024, 6, part2 = Day06::part2)
}