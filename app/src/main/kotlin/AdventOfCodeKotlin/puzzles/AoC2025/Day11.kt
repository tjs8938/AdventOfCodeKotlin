package AdventOfCodeKotlin.puzzles.AoC2025

import AdventOfCodeKotlin.oldframework.ExamplePuzzle
import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner
import AdventOfCodeKotlin.util.MemoizedFunction.Companion.memoize

class Day11 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {
            val connections = puzzle.getAsString().associate { row ->
                val id = row.substringBefore(":").trim()
                val connectsTo = row.substringAfter(": ").split(" ").map { it.trim() }
                id to connectsTo
            }

            val countPaths = memoize<String, Long> { from: String ->
                if (from == "out") {
                    1L
                } else {
                    connections[from]!!.sumOf { neighbor ->
                        this(neighbor)
                    }
                }
            }

            return countPaths("you").toString()
        }
        fun part2(puzzle: PuzzleInputProvider): String {
            val connections = puzzle.getAsString().associate { row ->
                val id = row.substringBefore(":").trim()
                val connectsTo = row.substringAfter(": ").split(" ").map { it.trim() }
                id to connectsTo
            }

            val countPaths = memoize<Pair<String, Int>, Long> { (from: String, found: Int) ->
                if (from == "out") {
                    if (found == 3) 1L else 0L
                } else {
                    connections.getOrElse(from, ::listOf).sumOf { neighbor ->
                        var newFound = found
                        if (neighbor == "dac") {
                            newFound = newFound or 1
                        } else if (neighbor == "fft") {
                            newFound = newFound or 2
                        }
                        this(neighbor to newFound)
                    }
                }
            }

            return countPaths("svr" to 0).toString()
        }
    }
}

fun main() {
    val ex = ExamplePuzzle("""
        aaa: you hhh
        you: bbb ccc
        bbb: ddd eee
        ccc: ddd eee fff
        ddd: ggg
        eee: out
        fff: out
        ggg: out
        hhh: ccc fff iii
        iii: out
    """.trimIndent(), "5")

    Runner.solve(2025, 11, 1, Day11::part1, ex)

    val ex2 = ExamplePuzzle("""
        svr: aaa bbb
        aaa: fft
        fft: ccc
        bbb: tty
        tty: ccc
        ccc: ddd eee
        ddd: hub
        hub: fff
        eee: dac
        dac: fff
        fff: ggg hhh
        ggg: out
        hhh: out
    """.trimIndent(), "2")
    Runner.solve(2025, 11, 2, Day11::part2, ex2)
}

