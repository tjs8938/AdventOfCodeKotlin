package AdventOfCodeKotlin.puzzles.AoC2023

import AdventOfCodeKotlin.framework.ExamplePuzzle
import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner
import AdventOfCodeKotlin.puzzles.AoC2023.Day10.Directions.SOUTH
import AdventOfCodeKotlin.puzzles.AoC2023.Day10.Directions.NORTH
import AdventOfCodeKotlin.puzzles.AoC2023.Day10.Directions.EAST
import AdventOfCodeKotlin.puzzles.AoC2023.Day10.Directions.WEST


private operator fun List<String>.get(pairIndex: Pair<Int, Int>): Char {
    return this[pairIndex.first][pairIndex.second]
}

class Day10 {
    enum class Directions(private val offset: Pair<Int, Int>) {
        NORTH(-1 to 0),
        EAST(0 to 1),
        SOUTH(1 to 0),
        WEST(0 to -1);

        operator fun plus(location: Pair<Int, Int>): Pair<Int, Int> {
            return (location.first + offset.first) to (location.second + offset.second)
        }
    }
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {

            val pipes = mutableMapOf<Pair<Int, Int>, MutableList<Pair<Int, Int>>>()
            val p = puzzle.getAsString()
            val startingPosition = buildPipeMap(p, pipes)

            pipes[startingPosition] = pipes.filter { it.value.contains(startingPosition) }.map { it.key }.toMutableList()
            var currentPosition = setOf(startingPosition)
            val visited = mutableSetOf(startingPosition)
            var stepCount = 0
            while (currentPosition.size > 1 || currentPosition.contains(startingPosition)) {
                currentPosition = currentPosition
                    .map { pipes[it]!! }
                    .flatten()
                    .filter { !visited.contains(it) }
                    .toSet()

                visited.addAll(currentPosition)

                stepCount++
            }

            return stepCount.toString()
        }

        private fun connect(
            current: Pair<Int, Int>,
            connected: Pair<Int, Int>,
            pipes: MutableMap<Pair<Int, Int>, MutableList<Pair<Int, Int>>>
        ) {
            if (!pipes.containsKey(current)) {
                pipes[current] = mutableListOf(connected)
            } else {
                pipes[current]!!.add(connected)
            }
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            // Create and populate a map where the key is the location of a pipe and the value is a list of locations of connected pipes
            val pipes = mutableMapOf<Pair<Int, Int>, MutableList<Pair<Int, Int>>>()
            val p = puzzle.getAsString()
            val startingPosition = buildPipeMap(p, pipes)

            // find the location of the starting pipe 'S'
            pipes[startingPosition] = pipes.filter { it.value.contains(startingPosition) }.map { it.key }.toMutableList()
            var currentPosition = setOf(startingPosition)

            // Starting at 'S' visit each pipe to determine which pipes are "in the loop"
            val visited = mutableSetOf(startingPosition)
            var stepCount = 0
            while (currentPosition.size > 1 || currentPosition.contains(startingPosition)) {
                currentPosition = currentPosition
                    .map { pipes[it]!! }
                    .flatten()
                    .filter { !visited.contains(it) }
                    .toSet()

                visited.addAll(currentPosition)

                stepCount++
            }

            // Find one pipe that is on the westernmost edge of the loop, which can be entered from the north
            var firstColumn = listOf<Pair<Int, Int>>()
            var column = 0
            while (firstColumn.isEmpty()) {
                firstColumn = visited.filter { it.second == column && listOf('|', 'L').contains(p[it])}
                column++
            }

            // Based on the current direction and the type of pipe I'm currently "on", which pipes need to be checked
            val checkSpots = mapOf(
                SOUTH to mapOf('|' to listOf(EAST), 'L' to listOf(), 'J' to listOf(EAST, SOUTH)),
                NORTH to mapOf('|' to listOf(WEST), '7' to listOf(), 'F' to listOf(NORTH, WEST)),
                EAST to mapOf('-' to listOf(NORTH), 'J' to listOf(), '7' to listOf(EAST, NORTH)),
                WEST to mapOf('-' to listOf(SOUTH), 'F' to listOf(), 'L' to listOf(SOUTH, WEST))
            )

            // Based on the current direction and the type of pipe I'm currently "on", which direction should I go next
            val turns = mapOf(
                SOUTH to mapOf('|' to SOUTH, 'J' to WEST, 'L' to EAST),
                NORTH to mapOf('|' to NORTH, '7' to WEST, 'F' to EAST),
                EAST to mapOf('-' to EAST, 'J' to NORTH, '7' to SOUTH),
                WEST to mapOf('-' to WEST, 'L' to NORTH, 'F' to SOUTH)
            )

            // Start on the western edge facing south
            val start = firstColumn.first()
            var spotOnLoop = start
            var direction = SOUTH
            var insideLoop = mutableSetOf<Pair<Int, Int>>()


            val startPipeType = determineStartPipeType(startingPosition, visited, p)
            do {
                var pipeType = p[spotOnLoop]
                if (pipeType == 'S') {
                    // if on that starting pipe, replace the pipe type
                    pipeType = startPipeType
                }
//                debugPrint(spotOnLoop, p)
                // look at each "inside" space that is adjacent to this pipe (on the "left")
                checkSpots[direction]!![pipeType]!!.forEach {
                    if (!visited.contains(it.plus(spotOnLoop))) {
                        // if this space is not part of the loop, it is "inside"
                        insideLoop.add(it.plus(spotOnLoop))
                    }
                }
                // change direction based on current direction and pipe type
                direction = turns[direction]!![pipeType]!!
                // move to the next pip
                spotOnLoop = direction.plus(spotOnLoop)
            } while (spotOnLoop != start) // finish when I've reached my western-edge starting point again

            do {
                // lazy BFS to fill in any holes in the "inside sections"
                val previousInsideLoop = insideLoop
                insideLoop = insideLoop.flatMap { listOf(it, NORTH.plus(it), SOUTH.plus(it), EAST.plus(it), WEST.plus(it)) }
                    .filter { !visited.contains(it) }
                    .toMutableSet()

            } while (previousInsideLoop.size != insideLoop.size)

            return insideLoop.size.toString()
        }

        fun part2b(puzzle: PuzzleInputProvider): String {
            // Create and populate a map where the key is the location of a pipe and the value is a list of locations of connected pipes
            val pipes = mutableMapOf<Pair<Int, Int>, MutableList<Pair<Int, Int>>>()
            val p = puzzle.getAsString().toMutableList()
            val startingPosition = buildPipeMap(p, pipes)

            // find the location of the starting pipe 'S'
            pipes[startingPosition] = pipes.filter { it.value.contains(startingPosition) }.map { it.key }.toMutableList()
            var currentPosition = setOf(startingPosition)

            // Starting at 'S' visit each pipe to determine which pipes are "in the loop"
            val visited = mutableSetOf(startingPosition)
            var stepCount = 0
            while (currentPosition.size > 1 || currentPosition.contains(startingPosition)) {
                currentPosition = currentPosition
                    .map { pipes[it]!! }
                    .flatten()
                    .filter { !visited.contains(it) }
                    .toSet()

                visited.addAll(currentPosition)

                stepCount++
            }

            val startPipeType = determineStartPipeType(startingPosition, visited, p)

            // replace any space that is not part of the pipe loop with '.'
            (p.indices).forEach { rowIndex ->
                val sb = StringBuilder(p[rowIndex])
                sb.indices.forEach { columnIndex ->
                    if (!visited.contains(rowIndex to columnIndex)) {
                        sb[columnIndex] = '.'
                    }
                    if ((rowIndex to columnIndex) == startingPosition) {
                        sb[columnIndex] = startPipeType
                    }
                }
                p[rowIndex] = sb.toString()
            }

            val regex = Regex("""(\.+|\||[FL]-*[J7])""")
            return p.sumOf { row ->
                var inLoop = false
                var count = 0
                regex.findAll(row)
                    .map { it.value }
                    .forEach {
                    if (it == "|" ||
                        (it.startsWith("L") && it.endsWith("7")) ||
                        (it.startsWith("F") && it.endsWith("J"))
                        ) {
                        inLoop = !inLoop
                    } else if (it.startsWith(".") && inLoop) {
                        count += it.length
                    }
                }
                count
            }.toString()
        }

        private fun buildPipeMap(
            p: List<String>,
            pipes: MutableMap<Pair<Int, Int>, MutableList<Pair<Int, Int>>>
        ): Pair<Int, Int> {
            var startingPosition: Pair<Int, Int>? = null
            p.forEachIndexed { rowIndex, row ->
                row.forEachIndexed { columnIndex, pipeChar ->
                    if (pipeChar == 'S') {
                        startingPosition = rowIndex to columnIndex
                    }
                    if (listOf('|', 'J', 'L').contains(pipeChar)) {
                        connect(rowIndex to columnIndex, rowIndex - 1 to columnIndex, pipes)
                    }
                    if (listOf('|', '7', 'F').contains(pipeChar)) {
                        connect(rowIndex to columnIndex, rowIndex + 1 to columnIndex, pipes)
                    }
                    if (listOf('-', 'F', 'L').contains(pipeChar)) {
                        connect(rowIndex to columnIndex, rowIndex to columnIndex + 1, pipes)
                    }
                    if (listOf('-', 'J', '7').contains(pipeChar)) {
                        connect(rowIndex to columnIndex, rowIndex to columnIndex - 1, pipes)
                    }
                }
            }
            return startingPosition!!
        }

        private fun determineStartPipeType(
            startingPosition: Pair<Int, Int>?,
            visited: MutableSet<Pair<Int, Int>>,
            p: List<String>
        ): Char {

            val southOfStart = SOUTH.plus(startingPosition!!)
                .let { n -> visited.contains(n) && listOf('|', 'J', 'L').contains(p[n]) }
            val northOfStart = NORTH.plus(startingPosition)
                .let { n -> visited.contains(n) && listOf('|', '7', 'F').contains(p[n]) }
            val eastOfStart = EAST.plus(startingPosition)
                .let { n -> visited.contains(n) && listOf('-', '7', 'J').contains(p[n]) }
            val westOfStart = WEST.plus(startingPosition)
                .let { n -> visited.contains(n) && listOf('-', 'F', 'L').contains(p[n]) }

            val startPipeType = if (northOfStart && eastOfStart) {
                'L'
            } else if (northOfStart && westOfStart) {
                'J'
            } else if (southOfStart && eastOfStart) {
                'F'
            } else if (southOfStart && westOfStart) {
                '7'
            } else if (northOfStart && southOfStart) {
                '|'
            } else {
                '-'
            }
            return startPipeType
        }

        private fun debugPrint(spotOnLoop: Pair<Int, Int>, p: List<String>) {
            p.forEachIndexed { rowIndex, row ->
                if (rowIndex == spotOnLoop.first) {
                    println(row.substring(0, spotOnLoop.second) + "X" + row.substring(spotOnLoop.second + 1))
                } else println(row)
            }
            println("-----------------------------------------------------")
        }
    }
}


fun main() {

    val ex1 = ExamplePuzzle("""
        ..F7.
        .FJ|.
        SJ.L7
        |F--J
        LJ...
    """.trimIndent())

//    assert(Day10.part1(ex1) == "8")

//    Runner.solve(2023, 10, part1 = Day10::part1)

    val ex2 = ExamplePuzzle("""
        .F----7F7F7F7F-7....
        .|F--7||||||||FJ....
        .||.FJ||||||||L7....
        FJL7L7LJLJ||LJ.L-7..
        L--J.L7...LJS7F-7L7.
        ....F-J..F7FJ|L7L7L7
        ....L7.F7||L7|.L7L7|
        .....|FJLJ|FJ|F7|.LJ
        ....FJL-7.||.||||...
        ....L---J.LJ.LJLJ...
    """.trimIndent())

//    assert(Day10.part2b(ex2) == "8")

    val ex3 = ExamplePuzzle("""
        FF7FSF7F7F7F7F7F---7
        L|LJ||||||||||||F--J
        FL-7LJLJ||||||LJL-77
        F--JF--7||LJLJ7F7FJ-
        L---JF-JLJ.||-FJLJJ7
        |F|F-JF---7F7-L7L|7|
        |FFJF7L7F-JF7|JL---7
        7-L-JL7||F7|L7F-7F7|
        L.L7LFJ|||||FJL7||LJ
        L7JLJL-JLJLJL--JLJ.L
    """.trimIndent())
//    assert(Day10.part2b(ex3) == "10")
    Runner.solve(2023, 10, part2 = Day10::part2b)
}