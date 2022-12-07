package AdventOfCodeKotlin.puzzles.AoC2022

import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner

class Day05 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {
            val (initialMap, instructions) = splitInput(puzzle.getAsString())

            val stacks = parseInitialStacks(initialMap)

            instructions.forEach {
                Regex("""move (\d+) from (\d+) to (\d+)""").matchEntire(it)!!
                    .destructured
                    .let { (c, s, e) ->
                        val count = c.toInt()
                        val startStack = s.toInt() - 1
                        val endStack = e.toInt() - 1

                        repeat(count) {stacks[endStack].add(stacks[startStack].removeLast())}
                     }
            }

            return stacks.map { it.last().toString() }.reduce { acc, s -> acc + s }
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            return solve(puzzle)
        }

        private fun solve(puzzle: PuzzleInputProvider): String {
            val (initialMap, instructions) = splitInput(puzzle.getAsString())

            val stacks = parseInitialStacks(initialMap)

            instructions.forEach { instruction ->
                Regex("""move (\d+) from (\d+) to (\d+)""").matchEntire(instruction)!!
                    .destructured
                    .toList().map { it.toInt() }
                    .let { (c, s, e) ->
                        val tempStack = ArrayDeque<Char>()
                        repeat(c) { tempStack.add(stacks[s - 1].removeLast()) }
                        repeat(c) { stacks[e - 1].add(tempStack.removeLast()) }
                    }
            }

            return stacks.map { it.last().toString() }.reduce { acc, s -> acc + s }
        }

        fun splitInput(input: List<String>) : Pair<List<String>, List<String>> {
            return Pair(input.takeWhile { !it.startsWith(" 1") }, input.takeLastWhile {it != ""})
        }

        fun parseInitialStacks(input: List<String>) : List<ArrayDeque<Char>> {
            // 3 characters per stack (3N), with spaces in between (N - 1 spaces)
            val numStacks = (input[0].length + 1) / 4
            val stacks = mutableListOf<ArrayDeque<Char>>()
            repeat(numStacks) { stacks.add(ArrayDeque()) }
            input.reversed().forEach { stackString ->
                (0 until numStacks).forEach { stackIndex ->
                    val crate = stackString[stackIndex * 4 + 1]
                    if (crate != ' ') {
                        stacks[stackIndex].add(crate)
                    }
                }
            }

            return stacks
        }
    }
}


fun main() {

//    Runner.solve(2022, 5, part1 = Day05::part1)
    Runner.solve(2022, 5, part2 = Day05::part2)
}