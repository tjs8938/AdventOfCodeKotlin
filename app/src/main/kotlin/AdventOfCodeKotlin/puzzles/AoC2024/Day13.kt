package AdventOfCodeKotlin.puzzles.AoC2024

import AdventOfCodeKotlin.framework.ExamplePuzzle
import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner

class Day13 {
    companion object {

        class ClawMachine(val deltaA: Pair<Long, Long>, val deltaB: Pair<Long, Long>, val prize: Pair<Long, Long>)

        fun part1(puzzle: PuzzleInputProvider): Any {

            val input = puzzle.get().split("\n\n").map { clawInput ->
                val lines = clawInput.lines().map { Regex("""X.(\d*), Y.(\d*)""").find(it)!!.destructured.let { (x, y) -> x.toLong() to y.toLong() } }
                ClawMachine(lines[0], lines[1], lines[2])
            }

            return input.sumOf { clawMachine ->
                var location = clawMachine.prize
                var aPresses = 0
                while (location.first >= 0 && location.second >= 0) {
                    if (location.first % clawMachine.deltaB.first == 0L && location.second % clawMachine.deltaB.second == 0L
                        && location.first / clawMachine.deltaB.first == location.second / clawMachine.deltaB.second) {
                        return@sumOf location.first / clawMachine.deltaB.first + aPresses * 3
                    }
                    location = location - clawMachine.deltaA
                    aPresses ++
                }

                0
            }
        }

        fun part2(puzzle: PuzzleInputProvider): Any {
            val input = puzzle.get().split("\n\n").map { clawInput ->
                val lines = clawInput.lines().map { Regex("""X.(\d*), Y.(\d*)""").find(it)!!.destructured.let { (x, y) -> x.toLong() to y.toLong() } }
                ClawMachine(lines[0], lines[1], lines[2].first + 10000000000000L to lines[2].second + 10000000000000L)
            }

            return input.sumOf { clawMachine ->
                var location = clawMachine.prize
                val det = clawMachine.deltaA.first * clawMachine.deltaB.second - clawMachine.deltaA.second * clawMachine.deltaB.first
                val a = (location.first * clawMachine.deltaB.second - location.second * clawMachine.deltaB.first) / det
                val b = (clawMachine.deltaA.first * location.second - clawMachine.deltaA.second * location.first) / det

                if (a * clawMachine.deltaA.first + b * clawMachine.deltaB.first == location.first &&
                    a * clawMachine.deltaA.second + b * clawMachine.deltaB.second == location.second) {
                    return@sumOf a * 3 + b
                }

                0
            }
        }
    }
}

private operator fun Pair<Long, Long>.plus(delta: Pair<Long, Long>): Pair<Long, Long> {
    return first + delta.first to second + delta.second
}

private operator fun Pair<Long, Long>.minus(delta: Pair<Long, Long>): Pair<Long, Long> {
    return first - delta.first to second - delta.second
}

private operator fun Pair<Long, Long>.times(scale: Long): Pair<Long, Long> {
    return first * scale to second * scale
}

fun main() {

    val ex1 = ExamplePuzzle("""
        Button A: X+94, Y+34
        Button B: X+22, Y+67
        Prize: X=8400, Y=5400

        Button A: X+26, Y+66
        Button B: X+67, Y+21
        Prize: X=12748, Y=12176

        Button A: X+17, Y+86
        Button B: X+84, Y+37
        Prize: X=7870, Y=6450

        Button A: X+69, Y+23
        Button B: X+27, Y+71
        Prize: X=18641, Y=10279
    """.trimIndent())

    Day13.part1(ex1).let {
        println(it)
        assert(it == 480)
    }

    Day13.part2(ex1).let {
        println(it)
    }

    Runner.solve(2024, 13, part1 = Day13::part1)
    Runner.solve(2024, 13, part2 = Day13::part2)
}