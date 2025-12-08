package AdventOfCodeKotlin.puzzles.AoC2022

import AdventOfCodeKotlin.oldframework.PuzzleInputProvider
import AdventOfCodeKotlin.oldframework.Runner


class Day04 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider): String {
            return puzzle.getAsString().count {
                val regex = Regex("""(\d+)-(\d+),(\d+)-(\d+)""")
                regex.matchEntire(it)!!
                    .destructured
                    .let { (a1, a2, b1, b2) ->
                        (a1.toInt() in b1.toInt()..b2.toInt() && a2.toInt() in b1.toInt()..b2.toInt()) || (b1.toInt() in a1.toInt()..a2.toInt() && b2.toInt() in a1.toInt()..a2.toInt())
                    }
            }.toString()
        }

        fun part2(puzzle: PuzzleInputProvider): String {
            return puzzle.getAsString().count {
                val regex = Regex("""(\d+)-(\d+),(\d+)-(\d+)""")
                regex.matchEntire(it)!!
                    .destructured
                    .let { (a1, a2, b1, b2) ->
                        (a1.toInt() in b1.toInt()..b2.toInt() || a2.toInt() in b1.toInt()..b2.toInt()) || (b1.toInt() in a1.toInt()..a2.toInt() || b2.toInt() in a1.toInt()..a2.toInt())
                    }
            }.toString()
        }
    }
}


fun main() {
//    Runner.solve(2022, 4, part1 = Day04::part1)
    Runner.solve(2022, 4, part2 = Day04::part2)
}