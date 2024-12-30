package AdventOfCodeKotlin.puzzles.AoC2024

import AdventOfCodeKotlin.framework.ExamplePuzzle
import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner
import AdventOfCodeKotlin.util.Direction
import AdventOfCodeKotlin.util.Graph
import AdventOfCodeKotlin.util.Graph.Companion.buildGraph
import AdventOfCodeKotlin.util.Mover
import AdventOfCodeKotlin.util.Node
import java.util.PriorityQueue

class Day16 {
    companion object {

        class MazeState(position: Pair<Int, Int>, direction: Direction, val cost: Int, maze: Graph<Node>, val previous: MazeState? = null) :
            Mover<Node>(position, direction, maze), Comparable<MazeState> {
            override fun compareTo(other: MazeState): Int {
                return cost.compareTo(other.cost)
            }
        }

        fun part1(puzzle: PuzzleInputProvider): Any {

            val mazeInput = puzzle.getAsGrid()
            val maze = buildGraph(mazeInput, ::Node, { it != '#' })
            val startNode = maze.filterValues { it.label == 'S' }.values.first()
            val endNode = maze.filterValues { it.label == 'E' }.values.first()
            val end = endNode.y to endNode.x
            var current = startNode.y to startNode.x
            var dir = Direction.EAST

            val mazeStates = PriorityQueue(listOf(MazeState(current, dir, 0, maze)))
            val bestStates = mutableMapOf((current to dir) to 0)

            fun addState(loc: Pair<Int, Int>, dir: Direction, cost: Int) {
                if (bestStates.getOrDefault(loc to dir, Int.MAX_VALUE) > cost) {
                    bestStates[loc to dir] = cost
                    mazeStates.add(MazeState(loc, dir, cost, maze))
                }
            }

            while (true) {
                val mover = mazeStates.poll()
                if (mover.loc == end) {
                    return mover.cost
                }

                if (mover.canMoveForward()) {
                    addState(mover.dir + mover.loc, mover.dir, mover.cost + 1)
                }

                val left = mover.dir.turnLeft()
                if (mover.canMove(left)) {
                    addState(left + mover.loc, left, mover.cost + 1001)
                }
                val right = mover.dir.turnRight()
                if (mover.canMove(right)) {
                    addState(right + mover.loc, right, mover.cost + 1001)
                }
            }
        }

        fun part2(puzzle: PuzzleInputProvider): Any {
            val mazeInput = puzzle.getAsGrid()
            val maze = buildGraph(mazeInput, ::Node, { it != '#' })
            val startNode = maze.filterValues { it.label == 'S' }.values.first()
            val endNode = maze.filterValues { it.label == 'E' }.values.first()
            val end = endNode.y to endNode.x
            var current = startNode.y to startNode.x
            var dir = Direction.EAST

            val mazeStates = PriorityQueue(listOf(MazeState(current, dir, 0, maze)))
            val bestStates = mutableMapOf((current to dir) to 0)

            fun addState(loc: Pair<Int, Int>, dir: Direction, cost: Int, previous: MazeState) {
                if (bestStates.getOrDefault(loc to dir, Int.MAX_VALUE) >= cost) {
                    bestStates[loc to dir] = cost
                    mazeStates.add(MazeState(loc, dir, cost, maze, previous))
                }
            }

            val onBestPath = mutableSetOf(current)
            var bestCost = Int.MAX_VALUE

            while (mazeStates.isNotEmpty()) {
                val mover = mazeStates.poll()
                if (mover.cost > bestCost) {
                    continue
                } else if (mover.loc == end) {
                    bestCost = mover.cost

                    var m = mover
                    while (m != null) {
                        onBestPath.add(m.loc)
                        m = m.previous
                    }
                } else {
                    if (mover.canMoveForward()) {
                        addState(mover.dir + mover.loc, mover.dir, mover.cost + 1, mover)
                    }

                    val left = mover.dir.turnLeft()
                    if (mover.canMove(left)) {
                        addState(left + mover.loc, left, mover.cost + 1001, mover)
                    }
                    val right = mover.dir.turnRight()
                    if (mover.canMove(right)) {
                        addState(right + mover.loc, right, mover.cost + 1001, mover)
                    }
                }
            }
            return onBestPath.size
        }
    }
}

fun main() {

    val ex1 = ExamplePuzzle(
        """
            ###############
            #.......#....E#
            #.#.###.#.###.#
            #.....#.#...#.#
            #.###.#####.#.#
            #.#.#.......#.#
            #.#.#####.###.#
            #...........#.#
            ###.#.#####.#.#
            #...#.....#.#.#
            #.#.#.###.#.#.#
            #.....#...#.#.#
            #.###.#.#.#.#.#
            #S..#.....#...#
            ###############
        """.trimIndent()
    )
    Day16.part1(ex1).let {
        println(it)
        if (it != 7036) throw AssertionError()
    }

    val ex2 = ExamplePuzzle(
        """
        #################
        #...#...#...#..E#
        #.#.#.#.#.#.#.#.#
        #.#.#.#...#...#.#
        #.#.#.#.###.#.#.#
        #...#.#.#.....#.#
        #.#.#.#.#.#####.#
        #.#...#.#.#.....#
        #.#.#####.#.###.#
        #.#.#.......#...#
        #.#.###.#####.###
        #.#.#...#.....#.#
        #.#.#.#####.###.#
        #.#.#.........#.#
        #.#.#.#########.#
        #S#.............#
        #################
    """.trimIndent()
    )

    Day16.part1(ex2).let {
        println(it)
        if (it != 11048) throw AssertionError()
    }
    Runner.solve(2024, 16, part1 = Day16::part1)

    Day16.part2(ex1).let {
        println(it)
        if (it != 45) throw AssertionError()
    }

    Day16.part2(ex2).let {
        println(it)
        if (it != 64) throw AssertionError()
    }

    Runner.solve(2024, 16, part2 = Day16::part2)
}