package AdventOfCodeKotlin.puzzles.AoC2024

import AdventOfCodeKotlin.framework.ExamplePuzzle
import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner
import AdventOfCodeKotlin.util.MemoizedFunction.Companion.memoize
import kotlin.math.abs


class Day21 {
    companion object {
        val digital = mapOf(
            'A' to (3 to 2),
            '0' to (3 to 1),
            '1' to (2 to 0),
            '2' to (2 to 1),
            '3' to (2 to 2),
            '4' to (1 to 0),
            '5' to (1 to 1),
            '6' to (1 to 2),
            '7' to (0 to 0),
            '8' to (0 to 1),
            '9' to (0 to 2)
        )

        val directional = mapOf(
            'A' to (0 to 2),
            '^' to (0 to 1),
            '<' to (1 to 0),
            'v' to (1 to 1),
            '>' to (1 to 2),
        )

        fun move(from: Char, to: Char, keyPad: Map<Char, Pair<Int, Int>>): List<List<Char>> {
            var result = mutableListOf<Char>()
            val allResults = mutableListOf<List<Char>>()
            var (curY, curX) = keyPad[from]!!
            val (toY, toX) = keyPad[to]!!

            val vertChar = if (curY < toY) 'v' else '^'
            val latChar = if (curX < toX) '>' else '<'

            val vertList = List(abs(curY - toY)) { vertChar }
            val latList = List(abs(curX - toX)) { latChar }


            if (vertList.isEmpty() || latList.isEmpty()) {
                result.addAll(vertList)
                result.addAll(latList)
                result.add('A')
                allResults.add(result)
            } else {
                if (curX > 0 || toY != keyPad['A']!!.first) {
                    result.addAll(vertList)
                    result.addAll(latList)
                    result.add('A')
                    allResults.add(result)
                }
                if (curY != keyPad['A']!!.first || toX > 0) {
                    result = mutableListOf()
                    result.addAll(latList)
                    result.addAll(vertList)
                    result.add('A')
                    allResults.add(result)
                }
            }
            return allResults
        }

        val moveDirectional = memoize<Triple<Char, Char, Int>, Long> { (from, to, depth) ->
            var result = mutableListOf<Char>()
            val allResults = mutableListOf<List<Char>>()
            var (curY, curX) = directional[from]!!
            val (toY, toX) = directional[to]!!

            val vertChar = if (curY < toY) 'v' else '^'
            val latChar = if (curX < toX) '>' else '<'

            val vertList = List(abs(curY - toY)) { vertChar }
            val latList = List(abs(curX - toX)) { latChar }


            if (vertList.isEmpty() || latList.isEmpty()) {
                result.addAll(vertList)
                result.addAll(latList)
                result.add('A')
                allResults.add(result)
            } else {
                if (curX > 0 || toY != directional['A']!!.first) {
                    result.addAll(vertList)
                    result.addAll(latList)
                    result.add('A')
                    allResults.add(result)
                }
                if (curY != directional['A']!!.first || toX > 0) {
                    result = mutableListOf()
                    result.addAll(latList)
                    result.addAll(vertList)
                    result.add('A')
                    allResults.add(result)
                }
            }

            return@memoize if (depth == 1) {
                allResults.minOf { it.size }.toLong()
            } else {
                allResults.minOf { path ->
                    mutableListOf('A')
                        .apply { addAll(path) }
                        .zipWithNext().sumOf {
                            this(Triple(it.first, it.second, depth - 1))
                        }
                }
            }
        }

        fun part1(puzzle: PuzzleInputProvider): Any {
            return common(puzzle, 2)
        }

        fun common(puzzle: PuzzleInputProvider, depth: Int): Any {
            val input = puzzle.getAsString()

            val numerics = input.map { it.removeSuffix("A").toLong() }

            var codes = input.map { it.toList() }
            var sum = 0L

            codes.forEachIndexed { index, code ->

                val paths = mutableListOf('A')
                    .apply { addAll(code) }
                    .zipWithNext().map {
                        move(it.first, it.second, digital)
                    }

                val pathLengths = paths.map { pathGroup ->
                    pathGroup.minOf { path ->
                        mutableListOf('A')
                            .apply { addAll(path) }
                            .zipWithNext().sumOf {
                                moveDirectional(Triple(it.first, it.second, depth))
                            }
                    }
                }
                sum += pathLengths.sum() * numerics[index]
            }

            return sum
        }

        fun part2(puzzle: PuzzleInputProvider): Any {

            return common(puzzle, 25)
        }
    }
}


fun main() {

    val ex1 = ExamplePuzzle(
        """
        029A
        980A
        179A
        456A
        379A
    """.trimIndent()
    )
    Day21.part1(ex1).let {
        println(it)
        if (it != 126384L) error("Example 1 failed")
    }

    Runner.solve(2024, 21, part1 = Day21::part1)


    Runner.solve(2024, 21, part2 = Day21::part2)
}