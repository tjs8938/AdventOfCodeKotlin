package AdventOfCodeKotlin.puzzles.AoC2023

import AdventOfCodeKotlin.framework.ExamplePuzzle
import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner


class Day08 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {

            var input = puzzle.getAsString()
            val directions = input[0]
            input = input.drop(2)

            val regex = Regex("""(...) = .(...), (...).""")
            val nodes = input.associate {
                regex.matchEntire(it)!!
                    .destructured
                    .let { (start, left, right) ->
                        start to listOf(left, right)
                    }
            }

            var currentNode = "AAA"
            var step = 0
            var stepCount = 0
            val turnIndex = listOf('L', 'R')
            while (true) {
                currentNode = nodes[currentNode]!![turnIndex.indexOf(directions[step])]
                step = (step + 1) % directions.length
                stepCount++
                if (currentNode == "ZZZ") {
                    return stepCount.toString()
                }
            }

            return ""
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            var input = puzzle.getAsString()
            val directions = input[0]
            input = input.drop(2)

            val regex = Regex("""(...) = .(...), (...).""")
            val nodes = input.associate {
                regex.matchEntire(it)!!
                    .destructured
                    .let { (start, left, right) ->
                        start to listOf(left, right)
                    }
            }

            val currentNodes = nodes.filter { it.key.endsWith("A") }.map { it.key }.toMutableList()
            var step = 0
            var stepCount = 0L
            val turnIndex = listOf('L', 'R')
            val loops = mutableMapOf<Int, Long>()
            while (loops.size < currentNodes.size) {

                stepCount++

                (0 until currentNodes.size).forEach {
                    currentNodes[it] = nodes[currentNodes[it]]!![turnIndex.indexOf(directions[step])]
                    if (!loops.containsKey(it) && currentNodes[it].endsWith("Z")) {
                        loops[it] = stepCount
                    }
                }

                step = (step + 1) % directions.length

            }
            println(loops)

            return findLCMOfListOfNumbers(loops.values.toList()).toString()
        }


    }
}
fun findLCM(a: Long, b: Long): Long {
    val larger = if (a > b) a else b
    val maxLcm = a * b
    var lcm = larger
    while (lcm <= maxLcm) {
        if (lcm % a == 0L && lcm % b == 0L) {
            return lcm
        }
        lcm += larger
    }
    return maxLcm
}

fun findLCMOfListOfNumbers(numbers: List<Long>): Long {
    var result = numbers[0]
    for (i in 1 until numbers.size) {
        result = findLCM(result, numbers[i])
    }
    return result
}

fun main() {

    val ex1 = ExamplePuzzle("""
        LLR

        AAA = (BBB, BBB)
        BBB = (AAA, ZZZ)
        ZZZ = (ZZZ, ZZZ)
    """.trimIndent())

//    assert(Day08.part1(ex1) == "6")
//    Runner.solve(2023, 8, part1 = Day08::part1)

    val ex2 = ExamplePuzzle("""
        LR

        11A = (11B, XXX)
        11B = (XXX, 11Z)
        11Z = (11B, XXX)
        22A = (22B, XXX)
        22B = (22C, 22C)
        22C = (22Z, 22Z)
        22Z = (22B, 22B)
        XXX = (XXX, XXX)
    """.trimIndent())

    assert(Day08.part2(ex2) == "6")

    Runner.solve(2023, 8, part2 = Day08::part2)
}