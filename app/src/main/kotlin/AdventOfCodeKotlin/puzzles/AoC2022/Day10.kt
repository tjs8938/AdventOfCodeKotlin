package AdventOfCodeKotlin.puzzles.AoC2022

import AdventOfCodeKotlin.framework.ExamplePuzzle
import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner


class Day10 {
    companion object {
        private fun parseInput(puzzle: PuzzleInputProvider): MutableMap<Int, Int> {
            val values = mutableMapOf(0 to 1)
            var currentCycle = 1
            var runningTotal = 1

            puzzle.getAsString().forEach { instruction ->
                with(instruction) {
                    when {
                        startsWith("addx") -> {
                            currentCycle += 2
                            runningTotal += substring(5).toInt()
                            values[currentCycle] = runningTotal
                        }
                        else -> currentCycle += 1
                    }
                }
            }
            return values
        }

        private fun valueAt(values: MutableMap<Int, Int>, cycle: Int): Int {
            var cycleCounter = cycle
            while (!values.containsKey(cycleCounter)) {
                cycleCounter--
            }
            return values[cycleCounter]!!
        }

        private fun calcSignal(values: MutableMap<Int, Int>, cycle: Int) = valueAt(values, cycle) * cycle

        fun part1(puzzle: PuzzleInputProvider): String {
            val values = parseInput(puzzle)

            return generateSequence(20) { it + 40 }.take(6).map { calcSignal(values, it) }.sum().toString()
        }


        fun part2(puzzle: PuzzleInputProvider): String {
            val values = parseInput(puzzle)
            (1..240).forEach {
                val X = valueAt(values, it)
                val output = if (((it-1) % 40) in (X-1)..(X+1)) {
                    "#"
                } else {
                    "."
                }
                print(output)
                if (it % 40 == 0)
                {
                    println()
                }
            }
            return ""
        }
    }
}


fun main() {

    val example = ExamplePuzzle("addx 15\n" +
        "addx -11\n" +
        "addx 6\n" +
        "addx -3\n" +
        "addx 5\n" +
        "addx -1\n" +
        "addx -8\n" +
        "addx 13\n" +
        "addx 4\n" +
        "noop\n" +
        "addx -1\n" +
        "addx 5\n" +
        "addx -1\n" +
        "addx 5\n" +
        "addx -1\n" +
        "addx 5\n" +
        "addx -1\n" +
        "addx 5\n" +
        "addx -1\n" +
        "addx -35\n" +
        "addx 1\n" +
        "addx 24\n" +
        "addx -19\n" +
        "addx 1\n" +
        "addx 16\n" +
        "addx -11\n" +
        "noop\n" +
        "noop\n" +
        "addx 21\n" +
        "addx -15\n" +
        "noop\n" +
        "noop\n" +
        "addx -3\n" +
        "addx 9\n" +
        "addx 1\n" +
        "addx -3\n" +
        "addx 8\n" +
        "addx 1\n" +
        "addx 5\n" +
        "noop\n" +
        "noop\n" +
        "noop\n" +
        "noop\n" +
        "noop\n" +
        "addx -36\n" +
        "noop\n" +
        "addx 1\n" +
        "addx 7\n" +
        "noop\n" +
        "noop\n" +
        "noop\n" +
        "addx 2\n" +
        "addx 6\n" +
        "noop\n" +
        "noop\n" +
        "noop\n" +
        "noop\n" +
        "noop\n" +
        "addx 1\n" +
        "noop\n" +
        "noop\n" +
        "addx 7\n" +
        "addx 1\n" +
        "noop\n" +
        "addx -13\n" +
        "addx 13\n" +
        "addx 7\n" +
        "noop\n" +
        "addx 1\n" +
        "addx -33\n" +
        "noop\n" +
        "noop\n" +
        "noop\n" +
        "addx 2\n" +
        "noop\n" +
        "noop\n" +
        "noop\n" +
        "addx 8\n" +
        "noop\n" +
        "addx -1\n" +
        "addx 2\n" +
        "addx 1\n" +
        "noop\n" +
        "addx 17\n" +
        "addx -9\n" +
        "addx 1\n" +
        "addx 1\n" +
        "addx -3\n" +
        "addx 11\n" +
        "noop\n" +
        "noop\n" +
        "addx 1\n" +
        "noop\n" +
        "addx 1\n" +
        "noop\n" +
        "noop\n" +
        "addx -13\n" +
        "addx -19\n" +
        "addx 1\n" +
        "addx 3\n" +
        "addx 26\n" +
        "addx -30\n" +
        "addx 12\n" +
        "addx -1\n" +
        "addx 3\n" +
        "addx 1\n" +
        "noop\n" +
        "noop\n" +
        "noop\n" +
        "addx -9\n" +
        "addx 18\n" +
        "addx 1\n" +
        "addx 2\n" +
        "noop\n" +
        "noop\n" +
        "addx 9\n" +
        "noop\n" +
        "noop\n" +
        "noop\n" +
        "addx -1\n" +
        "addx 2\n" +
        "addx -37\n" +
        "addx 1\n" +
        "addx 3\n" +
        "noop\n" +
        "addx 15\n" +
        "addx -21\n" +
        "addx 22\n" +
        "addx -6\n" +
        "addx 1\n" +
        "noop\n" +
        "addx 2\n" +
        "addx 1\n" +
        "noop\n" +
        "addx -10\n" +
        "noop\n" +
        "noop\n" +
        "addx 20\n" +
        "addx 1\n" +
        "addx 2\n" +
        "addx 2\n" +
        "addx -6\n" +
        "addx -11\n" +
        "noop\n" +
        "noop\n" +
        "noop")

//    println(Day10.part2(example))
//    Runner.solve(2022, 10, part1 = Day10::part1)
    Runner.solve(2022, 10, part2 = Day10::part2)
}

