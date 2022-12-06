package AdventOfCodeKotlin.puzzles.AoC2020

import AdventOfCodeKotlin.framework.PuzzleInputProvider
import AdventOfCodeKotlin.framework.Runner

class Day06 {
    companion object {
        fun part1(puzzle: PuzzleInputProvider) : String {
            val groups = puzzle.get()
                .trim()
                .split("\n\n")

            val total = countByFunction(groups, Set<Char>::union)
            return total.toString()
        }

        fun part2(puzzle: PuzzleInputProvider) : String {
            val groups = puzzle.get()
                .trim()
                .split("\n\n")

            val total = countByFunction(groups, Set<Char>::intersect)
            return total.toString()
        }

        private fun countByFunction(groups: List<String>, operation: (Set<Char>, Set<Char>) -> Set<Char>) = groups.map { group ->
            group.split("\n").map(String::toSet)
        }.sumOf { groupAnswers ->
            groupAnswers.reduce { a, b -> operation(a, b)}.count()
        }
    }
}


fun main() {

    Runner.solve(2020, 6, Day06::part1, Day06::part2)
}