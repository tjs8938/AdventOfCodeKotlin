package AdventOfCodeKotlin.puzzles.AoC2023

import AdventOfCodeKotlin.framework.ExamplePuzzle
import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner
import java.lang.Math.sqrt
import kotlin.math.ceil
import kotlin.math.floor


class Day06 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {
            val inputs = puzzle.getAsString().map { it.substring(it.indexOf(":") + 1) }
                .map { it.split(" ").filter { e -> e != "" }.map { it.toInt() } }
            val races = inputs[0].zip(inputs[1])

            return races.map {
                (0..it.first).count { t -> it.first * t - t * t > it.second }
            }.reduce{acc, i ->  acc * i}.toString()
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            val inputs = puzzle.getAsString().map { it.substring(it.indexOf(":") + 1) }
                .map { it.filter { e -> e != ' ' }}.map { it.toLong() }


            val time = inputs[0]
            val dist = inputs[1]

            println("t=$time, d=$dist")
            val root1 = (time - sqrt((time*time - 4*dist).toDouble()) / 2)
            val root2 = (time + sqrt((time*time - 4*dist).toDouble()) / 2)

//            return (0..time).count { t -> time * t - t * t > dist }.toString()
            var ans = (floor(root2).toLong() - ceil(root1).toLong())
            if (time % 2 == 0L) {
                ans++
            }
            return ans.toString()
        }
    }
}


fun main() {

    val ex1 = ExamplePuzzle("""
        Time:      7  15   30
        Distance:  9  40  200
    """.trimIndent())

    assert(Day06.part1(ex1) == "288")
//    Runner.solve(2023, 6, part1 = Day06::part1)
    Runner.solve(2023, 6, part2 = Day06::part2)
}