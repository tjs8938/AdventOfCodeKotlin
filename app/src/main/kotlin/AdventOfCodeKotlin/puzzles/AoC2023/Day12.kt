package AdventOfCodeKotlin.puzzles.AoC2023

import AdventOfCodeKotlin.framework.ExamplePuzzle
import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner


class Day12 {
    companion object {
        fun part2(puzzle: PuzzleInputProvider): String {
            return findBrokenSprings(puzzle.getAsString()
                .map {
                    val splits = it.trim().split(" ")
                    (0 until 5).map { splits[0] }.joinToString("?") +
                        " " +
                        (0 until 5).map { splits[1] }.joinToString(",")
                })
                .toString()
        }

        fun part1b(puzzle: PuzzleInputProvider): String {
            return findBrokenSprings(puzzle.getAsString()).toString()
        }

        fun findBrokenSprings(input: List<String>): Long {
            val cache = mutableMapOf<Pair<String, String>, Long>()
            return input.sumOf {
                val splits = it.split(" ")
                countArrangements(splits[0], splits[1], cache)
            }
        }

        fun countArrangements(
            springs: String,
            groupString: String,
            cache: MutableMap<Pair<String, String>, Long>
        ): Long {
            return cache.getOrPut(springs to groupString) {
                var groups = groupString
                if (groups.isEmpty()) {
                    if (springs.contains("#")) {
                        0L
                    } else {
                        1L
                    }
                } else if (springs.isEmpty()) {
                    0L
                } else if (springs[0] == '.') {
                    countArrangements(springs.substring(1), groups, cache)
                } else if (springs[0] == '?') {
                    countArrangements(springs.substring(1), groups, cache) +
                        countArrangements("#" + springs.substring(1), groups, cache)
                } else {
                    val commaIndex = groups.indexOf(",")
                    val groupSize = if (commaIndex > 0) {
                        val s = groups.substring(0, commaIndex).toInt()
                        groups = groups.substring(commaIndex + 1)
                        s
                    } else {
                        val s = groups.toInt()
                        groups = ""
                        s
                    }
                    if (springs.length >= groupSize &&
                        !springs.substring(0, groupSize).contains('.') &&
                        (springs.length == groupSize || springs[groupSize] != '#')
                    ) {
                        if (springs.length == groupSize) {
                            countArrangements("", groups, cache)
                        } else {
                            countArrangements(springs.substring(groupSize + 1), groups, cache)
                        }
                    } else {
                        0L
                    }
                }
            }
        }
    }
}


fun main() {

    val ex1 = ExamplePuzzle(
        """
        ???.### 1,1,3
.??..??...?##. 1,1,3
?#?#?#?#?#?#?#? 1,3,1,6
????.#...#... 4,1,1
????.######..#####. 1,6,5
?###???????? 3,2,1
    """.trimIndent()
    )

    assert(Day12.part1b(ex1) == "21")

    Runner.solve(2023, 12, part1 = Day12::part1b)

    assert(Day12.part2(ex1) == "525152")
//    println("Solved the example")
    Runner.solve(2023, 12, part2 = Day12::part2)
}